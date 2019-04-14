package seedu.hms.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.CARL;
import static seedu.hms.testutil.TypicalCustomers.getTypicalHotelManagementSystem;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.ReservationManager;
import seedu.hms.model.ReservationModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.testutil.ReservationBuilder;

public class AddReservationCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullReservation_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReservationCommand(null);
    }

    @Test
    public void execute_customerAcceptedByModel_addSuccessful() throws Exception {
        ReservationModel modelStub =
            new ReservationManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
                new UserPrefs());
        Reservation validReservation = new ReservationBuilder().build();

        CommandResult commandResult = new AddReservationCommand(validReservation).execute(modelStub, commandHistory);

        assertEquals(String.format(AddReservationCommand.MESSAGE_SUCCESS, validReservation),
            commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Reservation alice = new ReservationBuilder().withPayer(ALICE).build();
        Reservation carl = new ReservationBuilder().withPayer(CARL).build();
        AddReservationCommand addAliceCommand = new AddReservationCommand(alice);
        AddReservationCommand addCarlCommand = new AddReservationCommand(carl);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddReservationCommand addAliceCommandCopy = new AddReservationCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand == null);

        // different customer -> returns false
        assertFalse(addAliceCommand.equals(addCarlCommand));
    }
}
