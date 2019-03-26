package seedu.hms.model.booking;

import java.util.function.Predicate;

/**
 * Tests that a {@code Booking}'s {@code Payer}'s IdentificationNo matches the payerId given.
 */
public class BookingContainsPayerPredicate implements Predicate<Booking> {
    private final String payerId;

    public BookingContainsPayerPredicate(String payerId) {
        this.payerId = payerId.substring(1);
    }

    @Override
    public boolean test(Booking booking) {
        return booking.getPayer().getIdNum().toString().equals(payerId);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof BookingContainsPayerPredicate // instanceof handles nulls
            && payerId.equals(((BookingContainsPayerPredicate) other).payerId)); // state check
    }

    @Override
    public String toString() {
        return payerId;
    }

}
