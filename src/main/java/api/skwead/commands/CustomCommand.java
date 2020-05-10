package api.skwead.commands;

import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.List;
import java.util.Map;

public abstract class CustomCommand extends BukkitCommand {

    protected CustomCommand(String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
    }

    public abstract Map<Integer, String> syntaxCheck(CommandSender commandSender, String s, String[] strings) throws SyntaxException;

    public abstract boolean execute(CommandSender commandSender, String s, String[] strings);
}
