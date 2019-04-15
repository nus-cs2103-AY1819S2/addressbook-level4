package seedu.hms.logic.commands;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.Model;
import seedu.hms.model.ReservationModel;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class ReservationCommand extends Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model   {@code Model} which the command should operate on.
     * @param history {@code CommandHistory} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(ReservationModel model, CommandHistory history) throws CommandException;

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return execute((ReservationModel) model, history);
    }

}
