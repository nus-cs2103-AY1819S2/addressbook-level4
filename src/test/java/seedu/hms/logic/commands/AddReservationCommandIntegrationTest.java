package seedu.hms.logic.commands;

//import static seedu.hms.logic.commands.CommandTestUtil.assertReservationCommandFailure;

import static seedu.hms.logic.commands.CommandTestUtil.assertReservationCommandSuccess;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import org.junit.Before;
import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.ReservationManager;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.RoomType;
import seedu.hms.testutil.ReservationBuilder;
import seedu.hms.testutil.TypicalCustomers;

public class AddReservationCommandIntegrationTest {
    private ReservationModel model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ReservationManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
            new UserPrefs());
    }

    @Test
    public void executeNewReservationSuccess() {
        Reservation validReservation = new ReservationBuilder()
            .withRoom(new RoomType(100, "Single Room", 500.0))
            .withDates("14/07/2019", "17/07/2019")
            .withPayer(TypicalCustomers.BOB)
            .withOtherUsers()
            .withComment("CoolComment")
            .build();
        ReservationModel expectedModel = new ReservationManager(
            new VersionedHotelManagementSystem(model.getHotelManagementSystem()), new UserPrefs());
        expectedModel.addReservation(validReservation);
        expectedModel.commitHotelManagementSystem();
        expectedModel.setSelectedReservation(validReservation);

        assertReservationCommandSuccess(new AddReservationCommand(validReservation), model, commandHistory,
            String.format(AddReservationCommand.MESSAGE_SUCCESS, validReservation), expectedModel);
    }
}
