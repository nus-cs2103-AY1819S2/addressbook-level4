package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.table.TableNumber;

/**
 * Clears all the order items for the selected table in Table Mode.
 */
public class ClearOrderCommand extends Command {
    public static final String COMMAND_WORD = "clearOrder";
    public static final String MESSAGE_SUCCESS = "All items from Table %1$s cleared!";
    public static final String MESSAGE_FAILURE = "Invalid table. Application should not be in this state.";

    @Override
    public CommandResult execute(Mode mode, Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getSelectedTable() == null || !model.getSelectedTable().isOccupied()) {
            // Shouldn't even be in Table Mode
            throw new CommandException(MESSAGE_FAILURE);
        }

        TableNumber tableNumber = model.getSelectedTable().getTableNumber();
        model.clearOrderItemsFrom(tableNumber);

        return new CommandResult(String.format(MESSAGE_SUCCESS, tableNumber), false, false);
    }
}
