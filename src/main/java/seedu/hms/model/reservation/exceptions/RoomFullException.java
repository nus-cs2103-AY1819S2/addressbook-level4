package seedu.hms.model.reservation.exceptions;

import seedu.hms.model.util.DateRange;

/**
 * Signals that the operation will result in a reservation exceeding the room's total capacity.
 */
public class RoomFullException extends RuntimeException {
    public RoomFullException(DateRange t) {
        super("Operation cannot be performed as the room has been booked fully between " + t);
    }
}
