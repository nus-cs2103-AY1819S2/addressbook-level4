package seedu.address.commons.core;

import seedu.address.logic.commands.management.AddCardCommand;
import seedu.address.logic.commands.management.AddLessonCommand;
import seedu.address.logic.commands.management.ChangeThemeCommand;
import seedu.address.logic.commands.management.DeleteCardCommand;
import seedu.address.logic.commands.management.DeleteLessonCommand;
import seedu.address.logic.commands.management.EditLessonCommand;
import seedu.address.logic.commands.management.ExitCommand;
import seedu.address.logic.commands.management.HelpCommand;
import seedu.address.logic.commands.management.HistoryCommand;
import seedu.address.logic.commands.management.ListCardsCommand;
import seedu.address.logic.commands.management.ListLessonsCommand;
import seedu.address.logic.commands.management.QuitLessonCommand;
import seedu.address.logic.commands.management.ReloadLessonsCommand;
import seedu.address.logic.commands.management.SetLessonTestValuesCommand;
import seedu.address.logic.commands.management.StartCommand;

/**
 * Container for user visible messages.
 */
public class Messages {
    /**
     * Feedback message displayed to the user when management mode is first entered.
     */
    public static final String MESSAGE_LESSON_COMMANDS =
            "<< Lesson View >>\nYou can now use the following commands:\n"
                    + "1.\t" + StartCommand.COMMAND_WORD
                    + ": Starts a lesson in Quiz mode.\n"
                    + "2.\t" + EditLessonCommand.COMMAND_WORD
                    + ": Opens a lesson in Card View for editing.\n"
                    + "3.\t" + AddLessonCommand.COMMAND_WORD
                    + ": Adds a lesson.\n"
                    + "4.\t" + DeleteLessonCommand.COMMAND_WORD
                    + ": Deletes a lesson.\n"
                    + "5.\t" + ReloadLessonsCommand.COMMAND_WORD
                    + ": Reloads all lessons from the data folder.\n"
                    + "6.\t" + ListLessonsCommand.COMMAND_WORD
                    + ": Lists all lessons with full details.\n"
                    + "7.\t" + ChangeThemeCommand.COMMAND_WORD
                    + ": Changes the theme.\n"
                    + "8.\t" + HistoryCommand.COMMAND_WORD
                    + ": Lists all entered commands.\n"
                    + "9.\t" + HelpCommand.COMMAND_WORD
                    + ": Shows user guide.\n"
                    + "10.\t" + ExitCommand.COMMAND_WORD
                    + ": Exits the program.";
    public static final String MESSAGE_CARD_COMMANDS =
            "<< Card View >>\nYou can now use the following commands:\n"
                    + "1.\t" + QuitLessonCommand.COMMAND_WORD
                    + ": Quits Card View and returns to Lesson View.\n"
                    + "2.\t" + SetLessonTestValuesCommand.COMMAND_WORD
                    + ": Sets the 2 test values for the lesson's flashcards.\n"
                    + "3.\t" + AddCardCommand.COMMAND_WORD
                    + ": Adds a card to the lesson.\n"
                    + "4.\t" + DeleteCardCommand.COMMAND_WORD
                    + ": Deletes the card at the specified INDEX of the card list.\n"
                    + "5.\t" + ListCardsCommand.COMMAND_WORD
                    + ": Lists all cards in the lesson with full details.\n"
                    + "6.\t" + ChangeThemeCommand.COMMAND_WORD
                    + ": Changes the theme.\n"
                    + "7.\t" + HistoryCommand.COMMAND_WORD
                    + ": Lists all entered commands.\n"
                    + "8.\t" + HelpCommand.COMMAND_WORD
                    + ": Shows user guide.\n"
                    + "9.\t" + ExitCommand.COMMAND_WORD
                    + ": Exits the program.";
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
            "This is a Card View command.\n"
            + "To open a lesson in Card View for editing, use this command:\n"
                    + EditLessonCommand.MESSAGE_USAGE;
    /**
     * Feedback message displayed to the user when attempting to use listLessons, addLesson,
     * deleteLesson, editLesson and reloadLessons command while lesson is opened. Prompts user
     * to exitLesson first.
     */
    public static final String MESSAGE_LESSON_VIEW_COMMAND =
            "This is a Lesson View command.\n"
            + "To exit Card View and return to Lesson View, use this command:\n"
                    + QuitLessonCommand.MESSAGE_USAGE;

    /**
     * This is a constants-only (utility) class which should not be instantiated.
     * Note that this is not a singleton class given that not even a single instance is allowed.
     */
    private Messages() { }
}
