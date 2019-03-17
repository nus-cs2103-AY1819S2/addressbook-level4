package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.CustomerModel;

/**
 * Reverts the {@code model}'s hms book to its previous state.
 */
public class UndoCommand extends CustomerCommand {

    public static final String COMMAND_ALIAS = "u";
    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(CustomerModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoHotelManagementSystem()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoHotelManagementSystem();
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
