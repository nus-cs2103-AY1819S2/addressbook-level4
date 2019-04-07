package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents an Appointment made by a patient.
 */
public class Appointment {

    public static final String MESSAGE_CONSTRAINTS = "Appointments can only be made in the future";

    private final int patientId;
    private final int doctorId;
    private final LocalDate date;
    private final LocalTime time;
    private final AppointmentStatus appointmentStatus;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param patientId A valid patientId.
     * @param doctorId A valid doctorId.
     * @param date A valid appointment date
     * @param time A valid appointment time
     */
    public Appointment(int patientId, int doctorId, LocalDate date, LocalTime time) {
        /**
         * Every field must be present and not null.
         */
        requireAllNonNull(date, time);
        //checkArgument(isValidAppointment(appointment), MESSAGE_CONSTRAINTS);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.appointmentStatus = AppointmentStatus.ACTIVE;
    }

    /**
     * Returns true if a given string is a valid appointment.
     */
    public static boolean isValidAppointment(String test) {
        return !test.trim().isEmpty();
    }

    @Override
    public String toString() {
        String str = "";
        str += "Appointment - ";
        str += "Patient ID: " + patientId;
        str += " Doctor ID: " + doctorId;
        str += " Date: " + date;
        str += " Time: " + time;
        return str;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public AppointmentStatus getAppointmentStatus() {
        return appointmentStatus;
    }

    public boolean isSameAppointment(Appointment that) {
        return this.equals(that);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Appointment that = (Appointment) o;

        if (this.doctorId != that.doctorId) {
            return false;
        }

        if (this.patientId != that.patientId) {
            return false;
        }

        if (!this.date.equals(that.date)) {
            return false;
        }

        if (!this.time.equals(that.time)) {
            return false;
        }

        if (!this.appointmentStatus.equals(that.appointmentStatus)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.date);
    }
}
