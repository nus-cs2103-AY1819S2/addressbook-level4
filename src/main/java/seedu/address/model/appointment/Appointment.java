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
    private final LocalDate dateOfAppt;
    private final LocalTime timeOfAppt;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param patientId A valid patientId.
     * @param doctorId A valid doctorId.
     * @param dateOfAppt A valid appointment date in the future
     * @param timeOfAppt A valid appointment time in the future
     */
    public Appointment(int patientId, int doctorId, LocalDate dateOfAppt, LocalTime timeOfAppt) {
        /**
         * Every field must be present and not null.
         */
        requireAllNonNull(dateOfAppt, timeOfAppt);
        //checkArgument(isValidAppointment(appointment), MESSAGE_CONSTRAINTS);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateOfAppt = dateOfAppt;
        this.timeOfAppt = timeOfAppt;
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
        str += " Date: " + dateOfAppt;
        str += " Time: " + timeOfAppt;
        return str;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public LocalDate getDateOfAppt() {
        return dateOfAppt;
    }

    public LocalTime getTimeOfAppt() {
        return timeOfAppt;
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

        if (!this.dateOfAppt.equals(that.dateOfAppt)) {
            return false;
        }

        if (!this.timeOfAppt.equals(that.timeOfAppt)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.dateOfAppt);
    }
}
