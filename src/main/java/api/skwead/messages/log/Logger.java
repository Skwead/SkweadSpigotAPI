package api.skwead.messages.log;

import api.skwead.messages.chat.ChatUtils;
import api.skwead.messages.chat.MessageType;
import api.skwead.storage.file.json.JSONConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 * A class to handle a logger common to the whole plugin
 * Ideally there should only be one Logger in action, so the indentation works perfectly
 */
@SuppressWarnings("unused")
public class Logger {
    private LogEntry processes = new LogEntry(true, "", new HashSet<>());
    private JSONConfig<LogEntry> config;
    private ChatUtils chatUtils;
    private JavaPlugin plugin;

    /**
     * Creates a logger given all the {@link LogEntry}s and a path to the configuration file.
     * The config will display a main {@link LogEntry} with all it's nested entries.
     * You should only use it to alter the <em>enabled</em> atribute, altering other atributes will break the Logger
     * @param processes All the subprocesses to be added, they shall not be nested.
     * @param path The path to the configuration file
     */
    public Logger(Set<LogEntry> processes, String path, ChatUtils chatUtils, JavaPlugin plugin) {
//        processes.forEach(sp -> this.processes.registerSubProcess(sp));
        this.chatUtils = chatUtils;
        this.plugin = plugin;

        this.setProcesses(processes);
        this.config = new JSONConfig<>(path, LogEntry.class);

        try {
            this.config.loadFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a message to the console.
     * @param type The {@link MessageType} of the message.
     * @param message The message to be printed
     */
    public void log(MessageType type, String message){
        switch (type){
            case SUCCESS:
                chatUtils.consoleMessage("&3[&b" + plugin.getName() + "&3]" + "&a[SUCESSO] &2"+message);
                break;
            case INFO:
                chatUtils.consoleMessage("&3[&b" + plugin.getName() + "&3]" + "&e[INFO   ] &6"+message);
                break;
            case ERROR:
                chatUtils.consoleMessage("&3[&b" + plugin.getName() + "&3]" + "&c[ERRO   ] &4"+message);
                break;
        }
    }

    // TODO: 04/04/2020 Take a look at what a binary search tree is, seems to be exactly wht I need
    private boolean isEnabled(String process){
        String[] subs = process.split("-");
        LogEntry p = null;
        for (LogEntry sp : this.processes.getSubProcesses())
            if(sp.getName().equals(process))
                p = sp;
            
        if(p == null) return false;
        
        if(subs.length == 0)
            return p.isEnabled();
        else 
            return isEnabled(p, process.substring(process.indexOf('-')));
    }

    private boolean isEnabled(LogEntry p, String process) {
        String[] subs = process.split("-");
        LogEntry le = null;
        for (LogEntry sp : p.getSubProcesses())
            if(sp.getName().equals(process))
                le = sp;

        if(le == null) return false;

        if(subs.length == 0)
            return le.isEnabled();
        else
            return isEnabled(le, process.substring(process.indexOf('-')));
    }
    /**
     * Makes a check to see if the process is doing alright.
     * Should be used once at the start and end of the process, to check
     * if the data given and the return are correct.
     * @param b The conditions to be met
     * @param proc The process name
     * @param successMessage Message to be displayed if it worked
     * @param errorMessage Message to indicate error
     */
    public void check(boolean b, String proc, String successMessage, String errorMessage) {
        if(b)
            log(MessageType.SUCCESS, '['+proc+']'+getIndent(proc)+successMessage);
        else
            log(MessageType.ERROR, '['+proc+']'+ getIndent(proc)+ errorMessage);
    }

    private String getIndent(String processName){
        int i = processName.split("-").length;

        String res = "";
        while (i > 0) {
            res += "   ";
            i--;
        }

        return res;
    }

    private void setProcesses(Set<LogEntry> processes) {
        Set<LogEntry> res = new HashSet<>();

        processes.forEach(sp -> res.add(parse(sp)));

        res.forEach(sp -> this.processes.registerSubProcess(sp));
    }

    private LogEntry parse(LogEntry sp) {
        /**/System.out.println("!   "+sp.getName());
        String[] components = sp.getName().split("-");
        String name = components.length>0 ? components[0] : sp.getName();

        LogEntry top = new LogEntry(sp.isEnabled(), name, new HashSet<>());

        LogEntry t = top;
        LogEntry temp;

        for (int i = 1; i < components.length; i++) {

            String subName ="";
            for (int j = 0; j <= i; subName += "-"+components[j++]);
            subName = subName.substring(1);

            temp = new LogEntry(sp.isEnabled(), subName, new HashSet<>());
            t.registerSubProcess(temp);
            t = temp;
        }

        return top;
    }
}
