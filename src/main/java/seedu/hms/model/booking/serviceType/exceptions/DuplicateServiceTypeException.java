package seedu.hms.model.booking.serviceType.exceptions;

/**
 * Signals that the operation will result in duplicate Customers (Customers are considered duplicates if they have the
 * same identity).
 */
public class DuplicateServiceTypeException extends RuntimeException {
    public DuplicateServiceTypeException() {
        super("Operation would result in duplicate service types");
    }
}
