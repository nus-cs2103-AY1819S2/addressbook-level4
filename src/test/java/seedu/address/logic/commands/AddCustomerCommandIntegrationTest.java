package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.CustomerManager;
import seedu.address.model.CustomerModel;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedAddressBook;
import seedu.address.model.customer.Customer;
import seedu.address.testutil.CustomerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCustomerCommand}.
 */
public class AddCustomerCommandIntegrationTest {

    private CustomerModel model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new CustomerManager(new VersionedAddressBook(getTypicalAddressBook()), new UserPrefs());
    }

    @Test
    public void execute_newCustomer_success() {
        Customer validCustomer = new CustomerBuilder()
                .withName("Unique Name")
                .withPhone("9293292")
                .withEmail("unique@name.com")
                .withIdNum("9292392")
                .withAddress("2, New Place, #01-321")
                .build();
        System.out.println(validCustomer);
        CustomerModel expectedModel = new CustomerManager(
                new VersionedAddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.addCustomer(validCustomer);
        expectedModel.commitAddressBook();

        assertCommandSuccess(new AddCustomerCommand(validCustomer), model, commandHistory,
            String.format(AddCustomerCommand.MESSAGE_SUCCESS, validCustomer), expectedModel);
    }

    @Test
    public void execute_duplicateCustomer_throwsCommandException() {
        Customer customerInList = model.getAddressBook().getCustomerList().get(0);
        assertCommandFailure(new AddCustomerCommand(customerInList), model, commandHistory,
            AddCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

}
