package seedu.hms.model.reservation.exceptions;

/**
 * Signals that the operation will result in a reservation outside the room's operational hours.
 */
public class RoomUnavailableException extends RuntimeException {
    public RoomUnavailableException() {
        super("Operation cannot be performed outside room hours");
    }
}
