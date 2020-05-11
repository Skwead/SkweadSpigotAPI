package api.skwead.commands;

import api.skwead.exceptions.exceptions.CommandSyntaxException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashSet;
import java.util.Set;

/**
 * Serves as {@link CustomCommand} factory.
 * Selects wich command to be ran.
 */
public class CommandBase implements CommandExecutor {
    private final Set<CustomCommand> commands = new HashSet<>();
    private final String name;
    private final String help;

    /**
     * Builds a command given the name and help message.
     * @param name The command name.
     * @param help The help message
     */
    public CommandBase(String name, String help) {
        this.name = name;
        this.help = help;
    }

    /**
     * Adds a command to this base
     * @param cmd the command to be added.
     */
    public void registerCmd(CustomCommand cmd){
        commands.add(cmd);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        for (CustomCommand cmd : commands) {
            if (cmd.getName().equals(strings[0])) {
                cmd.run(commandSender, s, strings);
                return true;
            }
        }

        new CommandSyntaxException(commandSender, help, getSyntax()).showError();

        return true;
    }

    /**
     * Returns the base syntax.
     * @return the base syntax.
     */
    private String getSyntax() {
        StringBuilder cmd = new StringBuilder(this.name + " <");

        for (CustomCommand command : commands)
            cmd.append(command.getName()).append(" | ");

        cmd.append(">");

        return cmd.toString();
    }
}
