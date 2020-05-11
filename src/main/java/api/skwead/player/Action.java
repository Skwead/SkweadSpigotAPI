package api.skwead.player;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;

@SuppressWarnings("unused")
public interface Action<T extends Event> {
    boolean validate(CommandSender sender);

    void execute(CommandSender sender, T event);
}
