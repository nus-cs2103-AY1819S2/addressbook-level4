package seedu.address.model.appointment;

/**
 * Represents the status of an Appointment.
 */
public enum AppointmentStatus {
    ACTIVE("The appointment is active."),
    CANCELLED("The appointment has been cancelled."),
    COMPLETED("The appointment was completed."),
    MISSED("The appointment was missed.");

    /** The message of the appointment status. */
    private final String message;

    AppointmentStatus(String message) {
        this.message = message;
    }
}
