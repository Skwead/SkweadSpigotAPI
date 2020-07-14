package api.skwead.exceptions.exceptions;

import api.skwead.exceptions.ChatLoggedException;
import org.bukkit.command.CommandSender;

public class CommandException extends ChatLoggedException {
    private final String syntax;

    public CommandException(CommandSender troubleMaker, String message, String syntax) {
        super(message, troubleMaker);
        this.syntax = syntax;
    }

    @Override
    public void showError() {
        super.showError();
        super.getTroubleMaker().sendMessage(syntax);
    }
}
