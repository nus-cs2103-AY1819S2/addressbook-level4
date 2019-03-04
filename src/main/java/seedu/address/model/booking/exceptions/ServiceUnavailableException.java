package seedu.address.model.booking.exceptions;

/**
 * Signals that the operation will result in a booking outside the service's operational hours.
 */
public class ServiceUnavailableException extends RuntimeException {
    public ServiceUnavailableException() {
        super("Operation cannot be performed outside service hours");
    }
}
