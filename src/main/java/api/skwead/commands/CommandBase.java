package api.skwead.commands;

import api.skwead.exceptions.exceptions.CommandSyntaxException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Serves as {@link CustomCommand} factory.
 * Selects wich command to be ran.
 */
public class CommandBase extends BukkitCommand {
    private final Set<CustomCommand> commands = new HashSet<>();

    /**
     * Builds a CommandBase based on the parameters
     * @param name the command name
     * @param description the command description
     * @param usageMessage the message displayed when the command is not succsessfully ran
     * @param aliases the command aliases
     */
    protected CommandBase(String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }


    /**
     * Adds a command to this base
     * @param cmd the command to be added.
     */
    public void registerCmd(CustomCommand cmd){
        commands.add(cmd);
    }

    /**
     * The logic to run any subcommand
     * @param commandSender who sent the command
     * @param s label ig
     * @param strings the args for the command
     * @return true
     */
    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(strings.length == 0) {
            new CommandSyntaxException(commandSender, super.getDescription(), super.getUsage()).showError();
            return true;
        }

        for (CustomCommand cmd : commands) {
            if (cmd.getName().equals(strings[0])) {
                cmd.run(commandSender, strings);
                return true;
            }
        }

        new CommandSyntaxException(commandSender, super.getDescription(), super.getUsage()).showError();

        return true;
    }
}
