package seedu.hms.model.reservation.roomType.exceptions;

/**
 * Signals that the operation will result in duplicate Customers (Customers are considered duplicates if they have the
 * same identity).
 */
public class DuplicateRoomTypeException extends RuntimeException {
    public DuplicateRoomTypeException() {
        super("Operation would result in duplicate service types");
    }
}
