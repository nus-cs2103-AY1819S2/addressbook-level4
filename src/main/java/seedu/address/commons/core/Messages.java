package seedu.address.commons.core;

import seedu.address.logic.commands.management.OpenLessonCommand;

/**
 * Container for user visible messages.
 */
public class Messages {
    /**
     * Feedback message displayed to the user when management mode is first entered.
     */
    public static final String MESSAGE_LESSON_COMMANDS = "Hi";
    /**
     * Feedback message displayed to the user when the command does not match any command patterns.
     */
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command.";
    /**
     * Feedback message displayed to the user when the command format is incorrect.
     */
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format. \n%1$s";
    /**
     * Feedback message displayed to the user when an invalid index is supplied.
     */
    public static final String MESSAGE_INVALID_INDEX = "Invalid index %1$d.";
    /**
     * Feedback message displayed to the user when user does not supply exactly 1 input for a specific
     * parameter.
     */
    public static final String MESSAGE_INVALID_INPUT = "Only one input for '%1$s' is allowed.";
    /**
     * Feedback message displayed to the user when user supplies empty value.
     */
    public static final String MESSAGE_EMPTY_INPUT = "Empty input for '%1$s' is not allowed.";
    /**
     * Feedback message displayed to the user when attempting to close lesson when no lesson is opened.
     */
    public static final String MESSAGE_NO_OPENED_LESSON = "No opened lesson found.\n"
            + "To open a lesson use " + OpenLessonCommand.COMMAND_WORD + '.';
}
