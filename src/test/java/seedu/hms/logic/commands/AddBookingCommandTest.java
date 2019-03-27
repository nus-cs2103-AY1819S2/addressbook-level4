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
import seedu.hms.model.BookingManager;
import seedu.hms.model.BookingModel;
import seedu.hms.model.UserPrefs;
import seedu.hms.model.VersionedHotelManagementSystem;
import seedu.hms.model.booking.Booking;
import seedu.hms.testutil.BookingBuilder;

public class AddBookingCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddBookingCommand(null);
    }

    @Test
    public void execute_customerAcceptedByModel_addSuccessful() throws Exception {
        BookingModel modelStub =
                new BookingManager(new VersionedHotelManagementSystem(getTypicalHotelManagementSystem()),
                        new UserPrefs());
        Booking validBooking = new BookingBuilder().build();

        CommandResult commandResult = new AddBookingCommand(validBooking).execute(modelStub, commandHistory);

        assertEquals(String.format(AddBookingCommand.MESSAGE_SUCCESS, validBooking),
                commandResult.getFeedbackToUser());
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void equals() {
        Booking alice = new BookingBuilder().withPayer(ALICE).build();
        Booking carl = new BookingBuilder().withPayer(CARL).build();
        AddBookingCommand addAliceCommand = new AddBookingCommand(alice);
        AddBookingCommand addCarlCommand = new AddBookingCommand(carl);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddBookingCommand addAliceCommandCopy = new AddBookingCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand == null);

        // different customer -> returns false
        assertFalse(addAliceCommand.equals(addCarlCommand));
    }
}
