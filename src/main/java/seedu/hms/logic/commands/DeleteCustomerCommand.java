package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.hms.commons.core.Messages;
import seedu.hms.commons.core.index.Index;
import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.customer.Customer;

/**
 * Deletes a customer identified using it's displayed index from the hms book.
 */
public class DeleteCustomerCommand extends CustomerCommand {

    public static final String COMMAND_ALIAS = "dc";
    public static final String COMMAND_WORD = "delete-customer";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes the customer identified by the index number used in the displayed customer list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_CUSTOMER_SUCCESS = "Deleted Customer: %1$s";

    private final Index targetIndex;

    public DeleteCustomerCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(CustomerModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Customer> lastShownList = model.getFilteredCustomerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        Customer customerToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCustomer(customerToDelete);
        model.commitHotelManagementSystem();
        return new CommandResult(String.format(MESSAGE_DELETE_CUSTOMER_SUCCESS, customerToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DeleteCustomerCommand // instanceof handles nulls
            && targetIndex.equals(((DeleteCustomerCommand) other).targetIndex)); // state check
    }
}
