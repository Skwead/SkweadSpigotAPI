package api.skwead.exceptions.exceptions;

import api.skwead.exceptions.ChatLoggedException;
import org.bukkit.command.CommandSender;

public class CommandSyntaxException extends ChatLoggedException {
    private final String syntax;

    public CommandSyntaxException(CommandSender troubleMaker, String message, String syntax) {
        super(message, troubleMaker);
        this.syntax = syntax;
    }

    @Override
    public void showError() {
        super.showError();
        super.getTroubleMaker().sendMessage(syntax);
    }
}
