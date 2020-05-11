package api.skwead.commands;

import api.skwead.exceptions.exceptions.CommandSyntaxException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.List;

public abstract class CustomCommand extends BukkitCommand {

    public CustomCommand(String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    public void run(CommandSender commandSender, String s, String[] strings){
        try {
            syntaxCheck(commandSender, s, strings);
        } catch (CommandSyntaxException e) {
            e.showError();
        }
    }

    /**
     * The logic to check wether the given args are valid
     * @param commandSender the one who sends the command
     * @param s the label ig
     * @param strings the args
     * @return the syntax id
     * @throws CommandSyntaxException if the syntax is not correct
     */
    public abstract int syntaxCheck(CommandSender commandSender, String s, String[] strings) throws CommandSyntaxException;

    /**
     * The logic to run the command
     * @param commandSender the sender
     * @param s the label ig
     * @param strings the args
     * @return true if the command is executed succsessfully
     */
    public abstract boolean execute(CommandSender commandSender, String s, String[] strings);
}
