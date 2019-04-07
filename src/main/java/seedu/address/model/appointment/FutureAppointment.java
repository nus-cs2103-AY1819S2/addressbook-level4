package seedu.address.model.appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import seedu.address.model.appointment.exceptions.AppointmentNotInFutureException;

/**
 * Represents an Appointment that must be created in the future compared to system time.
 */
public class FutureAppointment extends Appointment {

    /**
     * Constructs a {@code FutureAppointment}.
     *
     * @param patientId A valid patientId.
     * @param doctorId A valid doctorId.
     * @param date A valid appointment date in the future
     * @param time A valid appointment time in the future
     */
    public FutureAppointment(int patientId, int doctorId, LocalDate date, LocalTime time) {
        super(patientId, doctorId, date, time);
        if (!isFuture()) {
            throw new AppointmentNotInFutureException();
        }
    }

    /**
     * Checks if an appointment is indeed in the future compared to system time.
     *
     * @return true if the appointment is in the future.
     */
    public boolean isFuture() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime appointmentDateTime = LocalDateTime.of(super.getDate(), super.getTime());

        final boolean future = appointmentDateTime.isAfter(currentDateTime);
        return future;
    }
}
