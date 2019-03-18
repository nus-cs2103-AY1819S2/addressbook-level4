package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.exceptions.CommandException.MESSAGE_EXPECTED_MGT_MODEL;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.management.ManagementModel;

/**
 * This implements a {@link Command} which executes a command to delete a {@link Lesson} from the
 * {@code List<Lesson> lessons} loaded in memory. It requires a {@link ManagementModel}
 * to be passed into the {@link #execute(Model, CommandHistory)} command. The actual deletion
 * of the {@link Lesson} is carried out in the {@link ManagementModel}.
 */
public class DeleteLessonCommand implements Command {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "deleteLesson";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the lesson identified by the index number in the numbered lesson list."
            + " shown when the command \'listLessons\' is entered.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS = "Deleted lesson: %1$s";
    /**
     * The index of the lesson to be deleted when {@link #execute(Model, CommandHistory)}
     * is called.
     */
    private final int toDeleteIndex;

    /**
     * Creates an DeleteLessonCommand to delete the specified {@link Lesson}
     *
     * @param toDeleteIndex the index of the {@link Lesson} to be deleted
     */
    public DeleteLessonCommand(int toDeleteIndex) {
        this.toDeleteIndex = toDeleteIndex;
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
        // CommandException will be thrown if and only if LogicManager passes in the incorrect Model
        // In other words, only incorrect code will result in a CommandException being thrown
        if (!(model instanceof ManagementModel)) {
            throw new CommandException(MESSAGE_EXPECTED_MGT_MODEL);
        }

        ManagementModel mgtModel = (ManagementModel) model;

        mgtModel.deleteLesson(toDeleteIndex);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDeleteIndex));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteLessonCommand // instanceof handles nulls
                && toDeleteIndex == ((DeleteLessonCommand) other).toDeleteIndex);
    }
}
