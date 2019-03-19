package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@link seedu.address.logic.commands.Command}.
 */
public class CommandException extends Exception {
    public static final String MESSAGE_EXPECTED_MGT_MODEL =
            "Expected ManagementModel but received QuizModel instead.";
    public static final String MESSAGE_EXPECTED_QUIZ_MODEL =
            "Expected QuizModel but received ManagementModel instead.";

    public CommandException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CommandException} with the specified detail {@code message} and {@code cause}.
     */
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
