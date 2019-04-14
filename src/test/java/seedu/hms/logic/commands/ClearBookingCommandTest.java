package seedu.hms.logic.commands;

import static seedu.hms.logic.commands.CommandTestUtil.assertBookingCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.BookingManager;
import seedu.hms.model.BookingModel;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;

public class ClearBookingCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyBookingList_success() {
        BookingModel model = new BookingManager();
        BookingModel expectedModel = new BookingManager();
        expectedModel.commitHotelManagementSystem();
        assertBookingCommandSuccess(new ClearBookingCommand(), model, commandHistory,
            ClearBookingCommand.MESSAGE_SUCCESS,
            expectedModel);
    }

    @Test
    public void execute_nonEmptyBookingList_success() {
        BookingModel model =
            new BookingManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
        BookingModel expectedModel = new BookingManager(
            new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
        expectedModel.setClearBooking(new HotelManagementSystem());
        expectedModel.commitHotelManagementSystem();

        assertBookingCommandSuccess(new ClearBookingCommand(), model, commandHistory,
            ClearBookingCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
