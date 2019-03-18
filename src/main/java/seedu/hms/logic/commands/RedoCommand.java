package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.CustomerModel;

/**
 * Reverts the {@code model}'s hms book to its previously undone state.
 */
public class RedoCommand extends CustomerCommand {

    public static final String COMMAND_ALIAS = "r";
    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(CustomerModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoHotelManagementSystem()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoHotelManagementSystem();
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
