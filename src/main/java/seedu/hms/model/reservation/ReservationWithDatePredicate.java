package seedu.hms.model.reservation;

import java.util.function.Predicate;

import seedu.hms.model.util.DateRange;

/**
 * Tests that a {@code Reservation}'s {@code T}
 */
public class ReservationWithDatePredicate implements Predicate<Reservation> {
    private final DateRange dateRange;

    public ReservationWithDatePredicate(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    @Override
    public boolean test(Reservation reservation) {
        return reservation.getDates().withinDates(dateRange);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ReservationWithDatePredicate // instanceof handles nulls
            && dateRange.equals(((ReservationWithDatePredicate) other).dateRange)); // state check
    }

    @Override
    public String toString() {
        return dateRange.toString();
    }

}
