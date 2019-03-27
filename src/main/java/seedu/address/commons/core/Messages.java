package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CARD_DISPLAYED_INDEX = "The card index provided is invalid";
    public static final String MESSAGE_INVALID_FOLDER_DISPLAYED_INDEX = "The card folder index provided is invalid";
    public static final String MESSAGE_CARDS_LISTED_OVERVIEW = "%1$d cards listed!";
    public static final String MESSAGE_INVALID_COMMAND_OUTSIDE_TEST_SESSION = "This command is valid only inside a test"
            + " session";
    public static final String MESSAGE_INVALID_COMMAND_INSIDE_TEST_SESSION = "This command is valid only outside a test"
            + " session";

    public static final String MESSAGE_INVALID_ANSWER_COMMAND = "Answer command is valid only when a question is "
            + "displayed";
    public static final String MESSAGE_NO_NEGATIVE_INDEX = "Negative index not allowed !";
    public static final String MESSAGE_ILLEGAL_COMMAND_NOT_IN_FOLDER = "Command can only be executed in folder";
    public static final String MESSAGE_ILLEGAL_COMMAND_NOT_IN_HOME = "Command can only be executed in home directory";

    public static final String MESSAGE_INVALID_COMMAND_ON_EMPTY_FOLDER = "This command is not valid on an empty"
            + " folder";
    public static final String MESSAGE_INVALID_NEXT_COMMAND = "Next command is valid only when this question has been"
            + " answered";

    public static final String MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER = "Command can only be executed in folder";
    public static final String MESSAGE_INVALID_COMMAND_INSIDE_FOLDER = "Command can only be executed in home directory";
    public static final String MESSAGE_CSV_MANAGER_NOT_INITIALIZED = "Unable to carry out import and export commands";

}
