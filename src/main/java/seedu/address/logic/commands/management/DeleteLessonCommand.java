package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link ManagementCommand} which executes a command to delete a {@link Lesson} from the
 * {@code List<Lesson> lessons} loaded in memory. It requires a {@link ManagementModel}
 * to be passed into the {@link #execute(Model, CommandHistory)} command. The actual deletion
 * of the {@link Lesson} is carried out in the {@link ManagementModel}.
 */
public class DeleteLessonCommand extends ManagementCommand {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "deleteLesson";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the lesson identified by its index number in the numbered list"
            + " shown when the command \'listLessons\' is entered.\n"
            + "Parameter: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS = "Deleted lesson: %1$s";
    /**
     * The index of the lesson to be deleted when {@link #execute(Model, CommandHistory)}
     * is called.
     */
    private final Index targetIndex;

    /**
     * Creates an DeleteLessonCommand to delete the specified {@link Lesson}
     *
     * @param targetIndex the index of the {@link Lesson} to be deleted
     */
    public DeleteLessonCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

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
        int toDeleteIndex = targetIndex.getZeroBased();

        String lessonName;

        try {
            lessonName = mgtModel.getLesson(toDeleteIndex).getName();
            mgtModel.deleteLesson(toDeleteIndex);
        } catch (IllegalArgumentException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteLessonCommand.MESSAGE_USAGE), e);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, lessonName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLessonCommand // instanceof handles nulls
                && targetIndex.getZeroBased() == ((DeleteLessonCommand) other).targetIndex.getZeroBased());
    }
}
