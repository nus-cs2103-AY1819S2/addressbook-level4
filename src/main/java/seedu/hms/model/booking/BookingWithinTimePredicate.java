package seedu.hms.model.booking;

import java.util.function.Predicate;

import seedu.hms.model.util.TimeRange;

/**
 * Tests that a {@code Booking}'s {@code T}
 */
public class BookingWithinTimePredicate implements Predicate<Booking> {
    private final TimeRange timeRange;

    public BookingWithinTimePredicate(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    @Override
    public boolean test(Booking booking) {
        return booking.getTiming().withinTiming(timeRange);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookingWithinTimePredicate // instanceof handles nulls
                && timeRange.equals(((BookingWithinTimePredicate) other).timeRange)); // state check
    }

    @Override
    public String toString() {
        return timeRange.toString();
    }

}
