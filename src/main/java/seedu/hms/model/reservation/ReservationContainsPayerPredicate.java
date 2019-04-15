package seedu.hms.model.reservation;

import java.util.function.Predicate;

/**
 * Tests that a {@code Reservtion}'s {@code Payer}'s IdentificationNo matches the payerId given.
 */
public class ReservationContainsPayerPredicate implements Predicate<Reservation> {
    private final String payerId;

    public ReservationContainsPayerPredicate(String payerId) {
        this.payerId = payerId.trim();
    }

    @Override
    public boolean test(Reservation reservation) {
        if (payerId.isEmpty()) {
            return true;
        }
        return reservation.getPayer().getIdNum().toString().equals(payerId);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ReservationContainsPayerPredicate // instanceof handles nulls
            && payerId.equals(((ReservationContainsPayerPredicate) other).payerId)); // state check
    }

    @Override
    public String toString() {
        return payerId;
    }

}
