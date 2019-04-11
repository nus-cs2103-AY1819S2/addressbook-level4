package seedu.address.commons.core;

import seedu.address.logic.commands.management.AddCardCommand;
import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.logic.commands.management.CloseLessonCommand;
import seedu.address.logic.commands.management.DeleteCardCommand;
import seedu.address.logic.commands.management.DeleteLessonCommand;
import seedu.address.logic.commands.management.ListCardsCommand;
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
            "<< Lesson Overview >>\nYou can now use the following commands:\n"
                    + "1. " + AddLessonCommand.COMMAND_WORD
                    + ": Adds a lesson.\n"
                    + "2. " + DeleteLessonCommand.COMMAND_WORD
                    + ": Deletes a lesson.\n"
                    + "3. " + OpenLessonCommand.COMMAND_WORD
                    + ": Opens a lesson for editing.\n"
                    + "4. " + ReloadLessonsCommand.COMMAND_WORD
                    + ": Reloads all lessons from the data folder.\n"
                    + "5. " + ListLessonsCommand.COMMAND_WORD
                    + ": Lists all lessons in memory.";
    public static final String MESSAGE_CARD_COMMANDS =
            "<< Lesson Edit >>\nYou can now use the following commands:\n"
                    + "1. " + AddCardCommand.COMMAND_WORD
                    + ": Adds a card to the opened lesson.\n"
                    + "2. " + DeleteCardCommand.COMMAND_WORD
                    + ": Deletes the card at the specified INDEX of the card list.\n"
                    + "3. " + CloseLessonCommand.COMMAND_WORD
                    + ": Closes this lesson and saves all changes.\n"
                    + "4. " + ListCardsCommand.COMMAND_WORD
                    + ": Lists all cards in the opened lesson.";
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
    public static final String MESSAGE_OPENED_LESSON =
            "Close the opened lesson before using this command.\n"
            + "To close the opened use:\n" + CloseLessonCommand.MESSAGE_USAGE;

    private Messages() {}
}
