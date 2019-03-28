package seedu.address.commons.core;

import static seedu.address.logic.commands.management.OpenLessonCommand.COMMAND_WORD;

/**
 * Container for user visible messages.
 */
public class Messages {
    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format. \n%1$s";
    public static final String MESSAGE_INVALID_INDEX = "Invalid index %1$d.";
    /**
     * Feedback message displayed to the user when attempting to close lesson when no lesson is opened.
     */
    public static final String MESSAGE_NO_OPENED_LESSON = "No opened lesson found.\n"
            + "To open a lesson use " + COMMAND_WORD + '.';
}
