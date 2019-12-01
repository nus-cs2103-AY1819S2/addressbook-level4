package seedu.address.model.appointment;

import java.time.LocalDateTime;

import seedu.address.model.appointment.exceptions.AppointmentNotInFutureException;

/**
 * Represents an Appointment that must be created in the future compared to system time.
 */
public class FutureAppointment extends Appointment {

    public static final String MESSAGE_CONSTRAINT_FUTURE = "Appointments can only be made in the future";

    /**
     * Constructs a {@code FutureAppointment}.
     *
     * @param patientId A valid patientId.
     * @param doctorId A valid doctorId.
     * @param date A valid appointment date in the future
     * @param time A valid appointment time in the future
     */
    public FutureAppointment(AppointmentPatientId patientId, AppointmentDoctorId doctorId,
                             AppointmentDate date, AppointmentTime time) {
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
        LocalDateTime appointmentDateTime = LocalDateTime.of(super.getDate().date, super.getTime().time);

        final boolean future = appointmentDateTime.isAfter(currentDateTime);
        return future;
    }
}
