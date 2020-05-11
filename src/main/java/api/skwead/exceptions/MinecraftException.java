package api.skwead.exceptions;

import org.bukkit.command.CommandSender;

/**
 * A class to better handle and manage exceptions on minecraft servers
 */
public abstract class MinecraftException extends Exception{
    private final CommandSender troubleMaker;

    public MinecraftException(String message, CommandSender troubleMaker) {
        super(message);
        this.troubleMaker = troubleMaker;
    }

    /**
     * The method to show the error in any wanted way.
     */
    abstract void showError();

    public CommandSender getTroubleMaker() {
        return troubleMaker;
    }
}
