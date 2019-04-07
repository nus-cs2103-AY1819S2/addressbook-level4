package seedu.address.commons.core;

import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.logic.commands.management.CloseLessonCommand;
import seedu.address.logic.commands.management.DeleteLessonCommand;
import seedu.address.logic.commands.management.ListLessonsCommand;
import seedu.address.logic.commands.management.OpenLessonCommand;
import seedu.address.logic.commands.management.ReloadLessonsCommand;

/**
 * Container for user visible messages.
 */
public class Messages {
    /**
     * Feedback message displayed to the user when management mode is first entered.
     */
    public static final String MESSAGE_LESSON_COMMANDS =
            "Lessons Overview: \nYou can now use the following commands:\n"
                    + "1. " + ListLessonsCommand.COMMAND_WORD
                    + ": Lists all lessons in memory.\n"
                    + "2. " + AddLessonCommand.COMMAND_WORD
                    + ": Adds a lesson.\n"
                    + "3. " + DeleteLessonCommand.COMMAND_WORD
                    + ": Deletes a lesson.\n"
                    + "4. " + OpenLessonCommand.COMMAND_WORD
                    + ": Opens a lesson for editing.\n"
                    + "5. " + CloseLessonCommand.COMMAND_WORD
                    + ": Closes the opened lesson and save changes.\n"
                    + "6. " + ReloadLessonsCommand.COMMAND_WORD
                    + ": Reloads all lessons from the data folder.";
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
    public static final String MESSAGE_NO_OPENED_LESSON = "Open a lesson before using this command.\n"
            + "To open a lesson use:\n" + OpenLessonCommand.MESSAGE_USAGE;
    /**
     * Feedback message displayed to the user when attempting to use listLessons, addLesson,
     * deleteLesson, openLesson and reloadLessons command while lesson is opened. Prompts user
     * to closeLesson first.
     */
    public static final String MESSAGE_OPENED_LESSON = "Command not allowed while lesson is opened.\n"
            + "Close the opened lesson first by using " + CloseLessonCommand.COMMAND_WORD + '.';

    private Messages() {}
}
