package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.CustomerModel;
import seedu.address.model.customer.Customer;

/**
 * Generates the bill for a customer who is identified using displayed index from address book
 */
public class GenerateBillCommand extends CustomerCommand {

    public static final String COMMAND_ALIAS = "gb";
    public static final String COMMAND_WORD = "generatebill";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Generates bill for the customer identified by the index number used in the displayed customer list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_GENERATE_BILL_SUCCESS = "Bill generated for customer: %1$s";

    private final Index targetIndex;

    public GenerateBillCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(CustomerModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Customer> lastShownList = model.getFilteredCustomerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        Customer customerToGenerateBillFor = lastShownList.get(targetIndex.getZeroBased());

        return new CommandResult(String.format(MESSAGE_GENERATE_BILL_SUCCESS, customerToGenerateBillFor));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof GenerateBillCommand // instanceof handles nulls
            && targetIndex.equals(((GenerateBillCommand) other).targetIndex)); // state check
    }
}
