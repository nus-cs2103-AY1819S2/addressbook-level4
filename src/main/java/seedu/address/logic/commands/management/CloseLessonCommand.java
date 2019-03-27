package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link Command} which executes a command to 'close' the opened {@link Lesson}.
 * It requires a {@link ManagementModel} to be passed into the {@link #execute(Model, CommandHistory)}
 * command. The purpose of 'closing' a lesson is to save changes made to it and to either move on to
 * quiz mode or to edit another lesson.
 */
public class CloseLessonCommand extends ManagementCommand {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "closeLesson";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Closes the opened lesson.\n"
            + "Example: " + COMMAND_WORD;

    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS = "Closed lesson and saved changes: %1$s";
    /**
     * Feedback message displayed to the user when attempting to close lesson when no lesson is opened.
     */
    public static final String MESSAGE_NO_OPENED_LESSON = "No opened lesson found.";

    /**
     * Executes the command and returns the result message.
     *
     * @param model which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ManagementModel mgtModel = requireManagementModel(model);

        String lessonName;

        try {
            lessonName = mgtModel.closeLesson();
        } catch (NullPointerException e) {
            throw new CommandException(MESSAGE_NO_OPENED_LESSON, e);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, lessonName));
    }

    /**
     * Certain ManagementCommand objects require a call to storageManager to save the lessons to disk
     * after being executed.
     *
     * <br><br>When a lesson is added, a save is required.
     *
     * @return true given that a save to disk is required.
     */
    @Override
    public boolean isSaveRequired() {
        return true;
    }
}
