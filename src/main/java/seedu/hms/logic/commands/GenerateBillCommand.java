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
 * Generates the bill for a customer who is identified using displayed index from hms book
 */
public class GenerateBillCommand extends CustomerCommand {

    public static final String COMMAND_ALIAS = "gb";
    public static final String COMMAND_WORD = "generate-bill";

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
