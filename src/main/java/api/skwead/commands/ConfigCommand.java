package api.skwead.commands;

import api.skwead.exceptions.exceptions.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_16_R2.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class ConfigCommand extends BukkitCommand {
    private String ref;
    private final Set<ConfigCommand> subCommands = new HashSet<>();

    public ConfigCommand(CMD cmd){
        this(cmd.getName(), cmd.getDescription(), cmd.getUsageMessage(), new ArrayList<>(cmd.getAliases()), cmd.getRef());
    }

    protected ConfigCommand(String name, String description, String usageMessage, List<String> aliases, String ref) {
        super(name, description, usageMessage, aliases);
        this.ref = ref;
    }

    public final CMD serialize(){
        // String ref, String name, String description, String usageMessage, Set<String> aliases, Set<CMD> subCmds
        final Set<CMD> subCmds = new HashSet<>();
//        this.subCommands.forEach(configCommand -> subCmds.add(configCommand.serialize()));

        return new CMD(this.ref, this.getName(), this.description, this.usageMessage,
                new HashSet<>(this.getAliases()), subCmds);
    }

    @SuppressWarnings("unused")
    public ConfigCommand getSubCommand(String name){
        for (ConfigCommand subCommand : this.subCommands) {
            if(subCommand.getName().equals(name))
                return subCommand;
        }

        return null;
    }

    @SuppressWarnings("unused")
    public void addSubCommand(JavaPlugin plugin, ConfigCommand cc){
        this.subCommands.add(cc);

//        cc.register(plugin);
    }

    public void register(JavaPlugin plugin){
        ((CraftServer) plugin.getServer()).getCommandMap().register(this.getName(), this);
    }

    @Override
    public final boolean execute(CommandSender commandSender, String s, String[] strings) {
        try{
//            validate(commandSender, s, strings);
            run(commandSender, s, strings);
            return true;
        } catch (CommandException e){
            e.showError();
            return false;
        }
    }

    @SuppressWarnings("unused")
    public abstract int validate(CommandSender commandSender, String arg, String[] args) throws CommandException;

    public abstract void run(CommandSender commandSender, String arg, String[] args) throws CommandException;

    public String getRef() {
        return ref;
    }

    public Set<ConfigCommand> getSubCommands() {
        return subCommands;
    }

    protected void setRef(String ref) {
        this.ref = ref;
    }
}
