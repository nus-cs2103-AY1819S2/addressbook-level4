package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link ManagementCommand} which deletes a {@link Lesson} from the
 * {@code List<Lesson> lessons} loaded in memory.
 *
 * It requires a {@link ManagementModel} to be passed into the {@link #execute(Model, CommandHistory)}
 * command.
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
            + ": Deletes the lesson at the specified INDEX of the lesson list.\n"
            + "To view the lesson list, enter \'listLessons\'.\n"
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
     * Executes the command which deletes a {@link Lesson} from the {@code List<Lesson> lessons}
     * loaded in memory.
     *
     * @param model the {@link ManagementModel} the command should operate on.
     * @param history {@link CommandHistory} which the command should operate on.
     *
     * @return feedback message of the operation result for display
     * @throws CommandException if invalid index supplied or if the {@code model} passed in
     * is not a {@link ManagementModel}
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
            throw new CommandException(String.format(MESSAGE_INVALID_INDEX,
                    targetIndex.getOneBased()), e);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, lessonName));
    }

    /**
     * When a lesson is closed, the updated list of {@link Lesson} objects needs
     * to be saved to the hard disk.
     *
     * @return true given that a save to disk is required.
     */
    @Override
    public boolean isSaveRequired() {
        return true;
    }

    /**
     * Returns true if {@code other} is the same object or if it is also an {@link DeleteLessonCommand}
     * attempting to delete the same lesson.
     *
     * @param other the other object to compare this object to
     * @return true if {@code other} is the same object or if it is also an {@link DeleteLessonCommand}
     * attempting to delete the same lesson.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLessonCommand // instanceof handles nulls
                && targetIndex.getZeroBased() == ((DeleteLessonCommand) other).targetIndex.getZeroBased());
    }
}
