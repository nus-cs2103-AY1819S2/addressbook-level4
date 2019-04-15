package seedu.hms.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.hms.model.reservation.Reservation;
import seedu.hms.testutil.ReservationBuilder;

public class ReservationCardTest extends GuiUnitTest {

    @Test
    public void equals() {
        Reservation reservation = new ReservationBuilder().build();
        ReservationCard reservationCard = new ReservationCard(reservation, 0);

        ReservationCard copy = new ReservationCard(reservation, 0);

        assertTrue(reservationCard.equals(copy));

        // same object -> returns true
        assertTrue(reservationCard.equals(reservationCard));

        // null -> returns false
        assertFalse(reservationCard == null);

        // different types -> returns false
        assertFalse(reservationCard.equals(0));

        // different reservation, same index -> returns false
        Reservation differentReservation = new ReservationBuilder().withDates("03/10/2019", "05/10/2019").build();
        assertFalse(reservation.equals(new ReservationCard(differentReservation, 0)));

        // same reservation, different index -> returns false
        assertFalse(reservationCard.equals(new ReservationCard(reservation, 1)));
    }
}
