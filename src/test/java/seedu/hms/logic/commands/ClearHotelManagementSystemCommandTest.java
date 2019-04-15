package seedu.hms.logic.commands;

import static seedu.hms.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.Model;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;

public class ClearHotelManagementSystemCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyHotelManagementSystem_success() {
        Model model = new CustomerManager();
        CustomerModel expectedModel = new CustomerManager();
        expectedModel.commitHotelManagementSystem();

        assertCommandSuccess(new ClearHotelManagementSystemCommand(), model, commandHistory,
            ClearHotelManagementSystemCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyHotelManagementSystem_success() {
        CustomerModel model =
            new CustomerManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
        CustomerModel expectedModel = new CustomerManager(
            new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
        expectedModel.setHotelManagementSystem(new HotelManagementSystem());
        expectedModel.commitHotelManagementSystem();

        assertCommandSuccess(new ClearHotelManagementSystemCommand(), model, commandHistory,
            ClearHotelManagementSystemCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
