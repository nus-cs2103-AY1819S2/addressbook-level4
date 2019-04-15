package seedu.hms.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.hms.model.booking.Booking;
import seedu.hms.testutil.BookingBuilder;

public class BookingCardTest extends GuiUnitTest {

    @Test
    public void equals() {
        Booking booking = new BookingBuilder().build();
        BookingCard bookingCard = new BookingCard(booking, 0);

        BookingCard copy = new BookingCard(booking, 0);

        assertTrue(bookingCard.equals(copy));

        // same object -> returns true
        assertTrue(bookingCard.equals(bookingCard));

        // null -> returns false
        assertFalse(bookingCard == null);

        // different types -> returns false
        assertFalse(bookingCard.equals(0));

        // different booking, same index -> returns false
        Booking differentBooking = new BookingBuilder().withTiming(10, 15).build();
        assertFalse(booking.equals(new BookingCard(differentBooking, 0)));

        // same booking, different index -> returns false
        assertFalse(bookingCard.equals(new BookingCard(booking, 1)));
    }
}
