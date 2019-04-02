package seedu.hms.logic.commands;

import static seedu.hms.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.hms.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import org.junit.Before;
import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.customer.Customer;
import seedu.hms.testutil.CustomerBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCustomerCommand}.
 */
public class AddCustomerCommandIntegrationTest {

    private CustomerModel model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new CustomerManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
            new UserPrefs());
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
        CustomerModel expectedModel = new CustomerManager(
            new VersionedHotelManagementSystem(model.getHotelManagementSystem()), new UserPrefs());
        expectedModel.addCustomer(validCustomer);
        expectedModel.commitHotelManagementSystem();

        assertCommandSuccess(new AddCustomerCommand(validCustomer), model, commandHistory,
            String.format(AddCustomerCommand.MESSAGE_SUCCESS, validCustomer), expectedModel);
    }

    @Test
    public void execute_duplicateCustomer_throwsCommandException() {
        Customer customerInList = model.getHotelManagementSystem().getCustomerList().get(0);
        assertCommandFailure(new AddCustomerCommand(customerInList), model, commandHistory,
            AddCustomerCommand.MESSAGE_DUPLICATE_CUSTOMER);
    }

}
