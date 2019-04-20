package seedu.address.logic.commands.management;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_LESSON_VIEW_COMMAND;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.Model;

/**
 * This implements a {@link ManagementCommand} which loads all lessons from file again.
 *
 * It requires a {@link ManagementModel} to be passed into the {@link #execute(Model, CommandHistory)}
 * command.
 */
public class ReloadLessonsCommand extends ManagementCommand {
    /**
     * The word a user must enter to call this command.
     */
    public static final String COMMAND_WORD = "reload";
    /**
     * Instructions on command usage and parameters.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reloads all lessons from the data folder.\n";
    /**
     * Feedback message displayed to the user upon successful execution of this command
     */
    public static final String MESSAGE_SUCCESS = "Reloaded all lessons from the data folder.";

    /**
     * Executes the command which reloads the lessons from file into memory.
     *
     * @param model the {@link ManagementModel} the command should operate on.
     * @param history {@link CommandHistory} which the command should operate on.
     *
     * @return feedback message of the operation result for display
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ManagementModel mgtModel = requireManagementModel(model);

        if (mgtModel.isThereOpenedLesson()) {
            throw new CommandException(MESSAGE_LESSON_VIEW_COMMAND);
        }

        return new CommandResult(MESSAGE_SUCCESS, CommandResult.UpdateStorage.LOAD);
    }

    /**
     * Returns true if {@code other} is the same object or if it is also an {@link ReloadLessonsCommand}
     * attempting to reload lessons.
     *
     * @param other the other object to compare this object to
     * @return true if {@code other} is the same object or if it is also an {@link ReloadLessonsCommand}
     * attempting to reload lessons.
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReloadLessonsCommand); // instanceof handles nulls
    }
}
