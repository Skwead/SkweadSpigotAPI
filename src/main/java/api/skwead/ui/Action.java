package api.skwead.ui;

import api.skwead.exceptions.exceptions.InvalidActionException;
import org.bukkit.event.Event;

public interface Action<T extends Event> {
    /**
     * The method that executes the action if the rquirements are met.
     * @param event the event listened.
     */
    default void execute(T event) {
        try{
            validate(event);
        } catch (InvalidActionException e){
            e.showError();
            return;
        }

        run(event);
    }

    /**
     * Validates if the player can execute the event
     * @param event the event triggered
     * @throws InvalidActionException if the player does not meet the requirements to execute the event
     */
    void validate(T event) throws InvalidActionException;

    /**
     * The logic of the Handler
     * @param event the event trigered
     */
    void run(T event);
}
