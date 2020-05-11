package api.skwead.commands;

import api.skwead.exceptions.exceptions.CommandSyntaxException;
import org.bukkit.command.CommandSender;

public abstract class CustomCommand{
    private final String name;
    private final String description;
    private final String usage;

    public CustomCommand(String name, String description, String usageMessage) {
        this.name = name;
        this.description = description;
        this.usage = usageMessage;
    }

    /**
     * Runs the command and handles the syntax errors.
     * @param commandSender
     * @param args
     */
    public void run(CommandSender commandSender, String[] args){
        try {
            syntaxCheck(commandSender, args);
        } catch (CommandSyntaxException e) {
            e.showError();
            return;
        }

        execute(commandSender, args);
    }

    /**
     * The logic to check wether the given args are valid
     * @param commandSender the one who sends the command
     * @param args the args
     * @return the syntax id
     * @throws CommandSyntaxException if the syntax is not correct
     */
    public abstract int syntaxCheck(CommandSender commandSender, String[] args) throws CommandSyntaxException;

    /**
     * The logic to run the command
     * @param commandSender the sender
     * @param args the args
     * @return true if the command is executed succsessfully
     */
    public abstract boolean execute(CommandSender commandSender, String[] args);

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }
}
