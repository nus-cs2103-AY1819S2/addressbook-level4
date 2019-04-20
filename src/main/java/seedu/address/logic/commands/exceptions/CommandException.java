package seedu.address.logic.commands.exceptions;

/**
 * Thrown to indicate that the execution of a command has failed.
 */
public class CommandException extends Exception {
    /**
     * Constructs a CommandException with the specified detail message.
     *
     * @param message the detail message
     */
    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     *
     * @param message the detail message
     * @param cause the cause for the CommandException occurrence
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
