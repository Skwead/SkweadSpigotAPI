package api.skwead.player;

import org.bukkit.command.CommandSender;

@SuppressWarnings("unused")
public interface Action {
    boolean validate(CommandSender sender);

    void execute(CommandSender sender, Object... args);
}
