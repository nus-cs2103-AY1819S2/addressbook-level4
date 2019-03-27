package seedu.hms.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.hms.testutil.TypicalCustomers.ALICE;
import static seedu.hms.testutil.TypicalCustomers.CARL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.hms.model.booking.Booking;
import seedu.hms.testutil.BookingBuilder;

public class AddBookingCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddBookingCommand(null);
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
        assertFalse(addAliceCommand.equals(null));

        // different customer -> returns false
        assertFalse(addAliceCommand.equals(addCarlCommand));
    }
}
