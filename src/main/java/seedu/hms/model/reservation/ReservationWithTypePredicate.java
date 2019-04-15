package seedu.hms.model.reservation;

import java.util.function.Predicate;

/**
 * Tests that a {@code Reservation}'s {@code RoomType}'s IdentificationNo matches the serviceType given.
 */
public class ReservationWithTypePredicate implements Predicate<Reservation> {
    private final String roomType;

    public ReservationWithTypePredicate(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public boolean test(Reservation reservation) {
        if (roomType.isEmpty()) {
            return true; //always return true if roomType is empty
        }
        return reservation.getRoom().getName().equalsIgnoreCase(roomType);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ReservationWithTypePredicate// instanceof handles nulls
            && roomType.equals(((ReservationWithTypePredicate) other).roomType)); // state check
    }

    @Override
    public String toString() {
        return roomType;
    }

}
