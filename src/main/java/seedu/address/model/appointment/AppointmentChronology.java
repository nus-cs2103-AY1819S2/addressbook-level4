package seedu.address.model.appointment;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents the chronology of an appointment,
 * i.e. whether it is in the past or future compared to system time.
 */
public enum AppointmentChronology {
    FUTURE("The appointment is in the future."),
    PAST("The appointment is in the past.");

    public static final String MESSAGE_CONSTRAINTS = "Appointment chronology can only be the following:\n"
            + FUTURE.name() + ": " + FUTURE.message + " "
            + PAST.name() + ": " + PAST.message;
    private static LocalDateTime systemTime;
    /** The message of the appointment chronology. */
    private final String message;

    AppointmentChronology(String message) {
        this.message = message;
    }

    /**
     * Returns true if a given string is a valid appointment chronology.
     */
    public static boolean isValidAppointmentChronology(String test) {
        try {
            AppointmentChronology appointmentChronology = AppointmentChronology.valueOf(test);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    /**
     * Refresh the internal time to the current system time.
     * This method has to be called before using other methods such as
     * {@link #isInPast(Appointment)} and {@link #isInFuture(Appointment)}
     */
    public static void refreshCurrentTime() {
        AppointmentChronology.systemTime = LocalDateTime.now();
    }

    /**
     * Checks if a given appointment is in the past compared to system time.
     * @param appointment appointment to check.
     */
    public static boolean isInPast(Appointment appointment) {
        Objects.requireNonNull(systemTime);
        LocalDateTime appointmentDateTime =
                LocalDateTime.of(appointment.getDate().date, appointment.getTime().time);
        return appointmentDateTime.isBefore(systemTime);
    }

    /**
     * Checks if a given appointment is in the future compared to system time.
     * @param appointment appointment to check.
     */
    public static boolean isInFuture(Appointment appointment) {
        Objects.requireNonNull(systemTime);
        LocalDateTime appointmentDateTime =
                LocalDateTime.of(appointment.getDate().date, appointment.getTime().time);
        return appointmentDateTime.isAfter(systemTime);
    }
}
