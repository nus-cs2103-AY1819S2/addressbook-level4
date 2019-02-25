package seedu.address.model.booking.exceptions;

import seedu.address.model.util.TimeRange;

/**
 * Signals that the operation will result in a booking exceeding the service's total capacity.
 */
public class ServiceFullException extends RuntimeException {
    public ServiceFullException(TimeRange t) {
        super("Operation cannot be performed as the service has been booked fully between " + t);
    }
}
