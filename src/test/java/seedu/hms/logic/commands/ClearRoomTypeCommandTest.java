package seedu.hms.logic.commands;

import static seedu.hms.logic.commands.CommandTestUtil.assertReservationCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.HotelManagementSystem;
import seedu.hms.model.ReservationManager;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;

public class ClearRoomTypeCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyReservationList_success() {
        ReservationModel model = new ReservationManager();
        ReservationModel expectedModel = new ReservationManager();
        expectedModel.commitHotelManagementSystem();
        assertReservationCommandSuccess(new ClearRoomTypeCommand(), model, commandHistory,
            ClearRoomTypeCommand.MESSAGE_SUCCESS,
            expectedModel);
    }

    @Test
    public void execute_nonEmptyReservationList_success() {
        ReservationModel model =
            new ReservationManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
                new UserPrefs());
        ReservationModel expectedModel = new ReservationManager(
            new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()), new UserPrefs());
        expectedModel.setClearRoomTypes(new HotelManagementSystem());
        expectedModel.setClearReservation(new HotelManagementSystem());
        expectedModel.commitHotelManagementSystem();

        assertReservationCommandSuccess(new ClearRoomTypeCommand(), model, commandHistory,
            ClearRoomTypeCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
