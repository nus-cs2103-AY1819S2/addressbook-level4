package seedu.knowitall.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_MAX_COMMAND_LENGTH_EXCEEDED = "Maximum command length exceeded, commands should"
            + " be less than %d characters";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CARD_DISPLAYED_INDEX = "The card index provided is invalid";
    public static final String MESSAGE_INVALID_FOLDER_DISPLAYED_INDEX = "The card folder index provided is invalid";
    public static final String MESSAGE_CARDS_LISTED_OVERVIEW = "%1$d cards listed!";
    public static final String MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN = "This command is not valid outside a test"
            + " or report";
    public static final String MESSAGE_INVALID_ANSWER_COMMAND = "Answer command is valid only when a question is "
            + "displayed";
    public static final String MESSAGE_INVALID_REVEAL_COMMAND = "Reveal command is valid only when a question is "
            + "displayed";
    public static final String MESSAGE_NO_NEGATIVE_INDEX = "Negative index not allowed!";
    public static final String MESSAGE_INVALID_COMMAND_ON_EMPTY_FOLDER = "This command is not valid on an empty"
            + " folder";
    public static final String MESSAGE_INVALID_NEXT_COMMAND = "Next command is valid only when this question has been"
            + " answered";
    public static final String MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER = "Command can only be executed in folder";
    public static final String MESSAGE_INVALID_COMMAND_INSIDE_FOLDER = "Command can only be executed in home directory";
    public static final String MESSAGE_INVALID_NUMBER_OF_CARD_ARGUMENTS = "Cards can only accept a maximum of %d %s";
    public static final String MESSAGE_ILLEGAL_OPTION_CANNOT_BE_SAME_AS_ANSWER = "Incorrect MCQ options cannot be same"
            + " as the correct answer";
    public static final String MESSAGE_CSV_MANAGER_NOT_INITIALIZED = "Unable to carry out import and export commands";
    public static final String MESSAGE_INCORRECT_CSV_FILE_HEADER = "Incorrect Csv file headers. Check that the\n"
            + "csv file contains question,answer,hints,option,option,option header";
    public static final String MESSAGE_EMPTY_CSV_FILE = "Empty csv file!";
}
