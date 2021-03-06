package api.skwead.messages.log;

import api.skwead.messages.chat.ChatUtils;
import api.skwead.messages.chat.MessageType;
import api.skwead.storage.file.json.JSONConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * A class to handle a logger common to the whole plugin. Deals with simple debugging
 * Ideally there should only be one Logger in action, so the indentation works perfectly
 */
@SuppressWarnings("unused")
public class Logger {
    private final Set<Process> procs;
    private final JSONConfig<Process> conf;
    private final ChatUtils chatUtils;
    private short maxLen = 0;

    /**
     * Creates a Logger instance.
     * Compares the processes from the plugin (passed as arguments to this constructor) with the
     * ones stored in the config.
     *
     * @param procs The default processes
     */
    public Logger(Set<Process> procs, String path, ChatUtils chatUtils, JavaPlugin plugin) {
        this.chatUtils = chatUtils;
        this.conf = new JSONConfig<>(path, Process.class);

        Process main = conf.getData();

        if (main == null) {
            this.procs = procs;
        } else {
            Set<Process> confProc = this.decirialize(main);
//            /**/System.out.println("--c--");
//            /**/confProc.forEach(p -> System.out.println(p.getName()));
//            /**/System.out.println("--c--");

//            this.procs = areEqual(procs, confProc) ? confProc : procs;
            this.procs = procs.size() == confProc.size() ? confProc : procs;

            this.procs.forEach(p -> this.maxLen = p.getName().length() > this.maxLen ? (short) p.getName().length() : this.maxLen);
        }
//        /**/System.out.println("--m--");
//        /**/this.procs.forEach(p -> System.out.println(p.getName()));
//        /**/System.out.println("--m--");
        try {
            final Process top = new Process(main == null || main.isEnabled(), "", new HashSet<>());
            this.mount(top, this.procs);
            this.conf.setData(top);
            this.conf.saveFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isEnabled(String process) {
        if (!this.conf.getData().isEnabled())
            return false;
        for (Process proc : this.procs) {
            if (process.startsWith(proc.getName())) {
                if (!proc.isEnabled())
                    return false;
                if (process.equals(proc.getName()))
                    return proc.isEnabled();
            }
        }
        return false;
    }

    /**
     * Logs a process with the {@link MessageType#INFO} type
     *
     * @param process The process name
     * @param message The message to be displayed
     */
    public void log(String process, String message) {
        log(process, message, MessageType.INFO);
    }

    /**
     * Logs a process
     *
     * @param process     The process name
     * @param message     The message to be displayed
     * @param messageType The type of the message to be logged
     */
    public void log(String process, String message, MessageType messageType) {
        if (!isEnabled(process)) return;

        String procName = String.format("%" + -maxLen + "s", process);
        String msg = String.format("[%s] %s", procName, message);
        chatUtils.log(messageType, msg);
    }

    /**
     * Makes an assertion logging it's result with given error/sucess messages
     *
     * @param b       the assertion condition
     * @param proc    the process name
     * @param success the sucsess message
     * @param error   the error message
     */
    public void check(boolean b, String proc, String success, String error) {
        if (!isEnabled(proc)) return;

        if (b)
            log(proc, success, MessageType.SUCCESS);
        else
            log(proc, error, MessageType.ERROR);
    }

    private boolean areEqual(Set<Process> procs, Set<Process> confProc) {
//        boolean notEqual = false;

        for (Process proc : procs) {
            boolean found = false;
            for (Process proc2 : confProc) {
                if (proc.getName().equals(proc2.getName())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("no");
                return false;
            } else System.out.println("yes");
        }

        for (Process proc : confProc) {
            boolean found = false;
            for (Process proc2 : procs) {
                if (proc.getName().equals(proc2.getName())) {
                    found = true;
                    break;
                }
            }
//            if (!found) return false;
            if (!found) {
                System.out.println("no");
                return false;
            } else System.out.println("yes");
        }
        return true;
    }

    private Set<Process> decirialize(Process p) {
        Set<Process> res = new HashSet<>();

//        res.add(new Process(p.isEnabled(), p.getName(), new HashSet<>()));
        p.getSubProcesses().forEach(sp -> decirialize(sp, res));

        return res;
    }

    private void decirialize(Process p, Set<Process> res) {
        res.add(new Process(p.isEnabled(), p.getName(), new HashSet<>()));

        if (!p.getSubProcesses().isEmpty())
            for (Process sp : p.getSubProcesses()) {
//                res.add(new Process(sp.isEnabled(), sp.getName(), new HashSet<>()));
                decirialize(sp, res);
            }

    }

    private void mount(Process top, Set<Process> procs) {
        procs.forEach(p -> {
            if (!p.getName().contains("-")) {
                Process sp = new Process(p.isEnabled(), p.getName(), new HashSet<>());
                top.registerSubProcess(sp);
//                /**/System.out.println(sp.getName());
            }
        });

        recMount(top, procs);
    }

    private void recMount(Process top, Set<Process> procs) {
        top.getSubProcesses().forEach(sp ->
                procs.forEach(p -> {
//                    /**/System.out.println("! " + p.getName() + p.getName().split("-").length + " starts with " + sp.getName() + sp.getName().split("-").length + "? " +
//                            (p.getName().startsWith(sp.getName()) && !p.getName().equals(sp.getName())));
                    if (p.getName().startsWith(sp.getName()) && !p.getName().equals(sp.getName())
                            && p.getName().split("-").length == sp.getName().split("-").length + 1) {
//                        /**/System.out.println("-----s----");
//                        /**/System.out.println(p.getName());
//                        /**/System.out.println("-----s----");
                        sp.registerSubProcess(p);
                        recMount(p, procs);
                    }
                }));
    }
}
