package seedu.address.commons.core;

import seedu.address.logic.commands.management.AddCardCommand;
import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.logic.commands.management.CloseLessonCommand;
import seedu.address.logic.commands.management.DeleteCardCommand;
import seedu.address.logic.commands.management.DeleteLessonCommand;
import seedu.address.logic.commands.management.EditLessonCommand;
import seedu.address.logic.commands.management.ListCardsCommand;
import seedu.address.logic.commands.management.ListLessonsCommand;
import seedu.address.logic.commands.management.ReloadLessonsCommand;
import seedu.address.logic.commands.management.SetTestCommand;

/**
 * Container for user visible messages.
 */
public class Messages {
    /**
     * Feedback message displayed to the user when management mode is first entered.
     */
    public static final String MESSAGE_LESSON_COMMANDS =
            "<< Lesson View >>\nYou can now use the following commands:\n"
                    + "1. " + EditLessonCommand.COMMAND_WORD
                    + ": Opens a lesson in Card View for editing .\n"
                    + "2. " + AddLessonCommand.COMMAND_WORD
                    + ": Adds a lesson.\n"
                    + "3. " + DeleteLessonCommand.COMMAND_WORD
                    + ": Deletes a lesson.\n"
                    + "4. " + ReloadLessonsCommand.COMMAND_WORD
                    + ": Reloads all lessons from the data folder.\n"
                    + "5. " + ListLessonsCommand.COMMAND_WORD
                    + ": Lists all lessons in memory.";
    public static final String MESSAGE_CARD_COMMANDS =
            "<< Card View >>\nYou can now use the following commands:\n"
                    + "1. " + CloseLessonCommand.COMMAND_WORD
                    + ": Closes this opened lesson and sav and return to Lesson View.\n"
                    + "2. " + SetTestCommand.COMMAND_WORD
                    + ": Sets the 2 test values for the opened lesson's flashcards.\n"
                    + "3. " + AddCardCommand.COMMAND_WORD
                    + ": Adds a card to the opened lesson.\n"
                    + "4. " + DeleteCardCommand.COMMAND_WORD
                    + ": Deletes the card at the specified INDEX of the card list.\n"
                    + "5. " + ListCardsCommand.COMMAND_WORD
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
    public static final String MESSAGE_INVALID_INDEX = "Invalid index: %1$d.";
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
    public static final String MESSAGE_CARD_VIEW_COMMAND =
            "This is a Card View command. Open a lesson in Card View to use this command.\n"
            + "To open a lesson in Card View for editing:\n" + EditLessonCommand.MESSAGE_USAGE;
    /**
     * Feedback message displayed to the user when attempting to use listLessons, addLesson,
     * deleteLesson, editLesson and reloadLessons command while lesson is opened. Prompts user
     * to exitLesson first.
     */
    public static final String MESSAGE_LESSON_VIEW_COMMAND =
            "This is a Lesson View command. Exit Card View to return to Lesson View.\n"
            + "To exit Card View:\n" + CloseLessonCommand.MESSAGE_USAGE;

    /**
     * This is a constants-only (utility) class which should not be instantiated.
     * Note that this is not a singleton class given that not even a single instance is allowed.
     */
    private Messages() { }
}
