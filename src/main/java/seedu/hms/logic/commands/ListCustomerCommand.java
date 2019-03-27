package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.model.Model.PREDICATE_SHOW_ALL_CUSTOMERS;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.CustomerModel;

/**
 * Lists all customers in the hms book to the user.
 */
public class ListCustomerCommand extends CustomerCommand {

    public static final String COMMAND_ALIAS = "lc";
    public static final String COMMAND_WORD = "list-customers";

    public static final String MESSAGE_SUCCESS = "Listed all customers";


    @Override
    public CommandResult execute(CustomerModel model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredCustomerList(PREDICATE_SHOW_ALL_CUSTOMERS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
