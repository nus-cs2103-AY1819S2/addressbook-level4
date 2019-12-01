package seedu.address.model.appointment.exceptions;

/**
 * Signals that the Appointment is not set in the future compared to system time.
 */
public class AppointmentNotInFutureException extends RuntimeException {
    public AppointmentNotInFutureException() {
        super("Operation would result in creating an appointment in the past");
    }
}
