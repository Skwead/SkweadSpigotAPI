package api.skwead.exceptions.exceptions;

import api.skwead.exceptions.ChatLoggedException;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

/**
 * Thrown when any UI action is not allowed for not meeting the requirements
 */
public class InvalidActionException extends ChatLoggedException {

    private final Event event;

    public InvalidActionException(String message, CommandSender troubleMaker, org.bukkit.event.Event event) {
        super(message, troubleMaker);
        this.event = event;
    }

    /**
     * Sends a chat message to te sender containing the error message
     */
    @Override
    public void showError() {
        super.showError();
        if(event instanceof Cancellable)
            ((Cancellable) event).setCancelled(true);
    }
}
