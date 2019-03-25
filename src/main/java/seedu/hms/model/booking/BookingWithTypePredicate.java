package seedu.hms.model.booking;

import java.util.function.Predicate;

/**
 * Tests that a {@code Booking}'s {@code Payer}'s IdentificationNo matches the serviceType given.
 */
public class BookingWithTypePredicate implements Predicate<Booking> {
    private final String serviceType;

    public BookingWithTypePredicate(String serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public boolean test(Booking booking) {
        return booking.getService().getName().equalsIgnoreCase(serviceType);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookingWithTypePredicate // instanceof handles nulls
                && serviceType.equals(((BookingWithTypePredicate) other).serviceType)); // state check
    }

    @Override
    public String toString() {
        return serviceType;
    }

}
