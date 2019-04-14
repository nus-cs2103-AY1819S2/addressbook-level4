package seedu.hms.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_IDENTIFICATION_NUMBER;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.hms.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.hms.logic.CommandHistory;
import seedu.hms.logic.commands.exceptions.CommandException;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.customer.Customer;

/**
 * Adds a customer to the hms book.
 */
public class AddCustomerCommand extends CustomerCommand {

    public static final String COMMAND_ALIAS = "ac";
    public static final String COMMAND_WORD = "add-customer";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a customer to the hms book.\n"
        + "Parameters: "
        + PREFIX_NAME + "NAME "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_EMAIL + "EMAIL "
        + PREFIX_IDENTIFICATION_NUMBER + "IDENTIFICATION_NO "
        + "[" + PREFIX_DATE_OF_BIRTH + "DATE OF BIRTH] "
        + "[" + PREFIX_ADDRESS + "ADDRESS] "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_NAME + "John Doe "
        + PREFIX_PHONE + "98765432 "
        + PREFIX_EMAIL + "jd@eox.com "
        + PREFIX_IDENTIFICATION_NUMBER + "A123456Q "
        + PREFIX_DATE_OF_BIRTH + "28/05/1999 "
        + PREFIX_ADDRESS + "311, Clementi Ave 2"
        + PREFIX_TAG + "friends ";

    public static final String MESSAGE_SUCCESS = "New customer added: %1$s";
    public static final String MESSAGE_DUPLICATE_CUSTOMER = "This customer already exists in the hms book";

    private final Customer toAdd;

    /**
     * Creates an AddCustomerCommand to add the specified {@code Customer}
     */
    public AddCustomerCommand(Customer customer) {
        requireNonNull(customer);
        toAdd = customer;
    }

    @Override
    public CommandResult execute(CustomerModel model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasCustomer(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CUSTOMER);
        }

        model.addCustomer(toAdd);
        model.commitHotelManagementSystem();
        model.setSelectedCustomer(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddCustomerCommand // instanceof handles nulls
            && toAdd.equals(((AddCustomerCommand) other).toAdd));
    }
}
