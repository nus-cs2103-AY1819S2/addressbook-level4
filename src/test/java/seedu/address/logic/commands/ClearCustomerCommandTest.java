package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.CustomerManager;
import seedu.address.model.CustomerModel;
import seedu.address.model.HotelManagementSystem;
import seedu.address.model.Model;
import seedu.address.model.UserPrefs;
import seedu.address.model.VersionedHotelManagementSystem;

public class ClearCustomerCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyHotelManagementSystem_success() {
        Model model = new CustomerManager();
        CustomerModel expectedModel = new CustomerManager();
        expectedModel.commitHotelManagementSystem();

        assertCommandSuccess(new ClearCustomerCommand(), model, commandHistory, ClearCustomerCommand.MESSAGE_SUCCESS,
            expectedModel);
    }

    @Test
    public void execute_nonEmptyHotelManagementSystem_success() {
        CustomerModel model =
            new CustomerManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
        CustomerModel expectedModel = new CustomerManager(
            new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
        expectedModel.setHotelManagementSystem(new HotelManagementSystem());
        expectedModel.commitHotelManagementSystem();

        assertCommandSuccess(new ClearCustomerCommand(), model, commandHistory, ClearCustomerCommand.MESSAGE_SUCCESS,
            expectedModel);
    }

}
