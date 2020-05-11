package api.skwead.exceptions;

import org.bukkit.command.CommandSender;

/**
 * Base class for exceptions that show errors to {@link org.bukkit.entity.Player}s
 */
public class ChatLoggedException extends MinecraftException{
    public ChatLoggedException(String message, CommandSender troubleMaker) {
        super(message, troubleMaker);
    }

    /**
     * Sends the {@link Exception} meesage to the {@link CommandSender} whose action threw it
     */
    @Override
    public void showError() {
        super.getTroubleMaker().sendMessage(super.getMessage());
    }
}
