package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import seedu.address.logic.CommandHistory;
import seedu.address.model.CustomerModel;

/**
 * Lists all customers in the address book to the user.
 */
public class ListCommand extends CustomerCommand {

    public static final String COMMAND_ALIAS = "lc";
    public static final String COMMAND_WORD = "listcustomers";

    public static final String MESSAGE_SUCCESS = "Listed all customers";


    @Override
    public CommandResult execute(CustomerModel model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
