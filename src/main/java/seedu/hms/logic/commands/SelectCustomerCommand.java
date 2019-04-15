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
 * Selects a customer identified using it's displayed index from the hms book.
 */
public class SelectCustomerCommand extends CustomerCommand {

    public static final String COMMAND_ALIAS = "s";
    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Selects the customer identified by the index number used in the displayed customer list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_CUSTOMER_SUCCESS = "Selected Customer: %1$s";

    private final Index targetIndex;

    public SelectCustomerCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(CustomerModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Customer> filteredCustomerList = model.getFilteredCustomerList();

        if (targetIndex.getZeroBased() >= filteredCustomerList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        model.setSelectedCustomer(filteredCustomerList.get(targetIndex.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_SELECT_CUSTOMER_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SelectCustomerCommand // instanceof handles nulls
            && targetIndex.equals(((SelectCustomerCommand) other).targetIndex)); // state check
    }
}
