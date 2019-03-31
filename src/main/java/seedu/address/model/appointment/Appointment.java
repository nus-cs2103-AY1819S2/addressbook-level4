package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents an Appointment made by a patient.
 */
public class Appointment {

    public static final String MESSAGE_CONSTRAINTS = "Appointments can only be made in the future";

    private final int patientId;
    private final int doctorId;
    private final String dateOfAppt;
    private final String timeOfAppt;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param patientId A valid patientId.
     * @param doctorId A valid doctorId.
     * @param dateOfAppt A valid appointment date in the future
     * @param timeOfAppt A valid appointment time in the future
     */
    public Appointment(int patientId, int doctorId, String dateOfAppt, String timeOfAppt) {
        requireNonNull(dateOfAppt);
        requireNonNull(timeOfAppt);
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
        return dateOfAppt;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getDateOfAppt() {
        return dateOfAppt;
    }

    public String getTimeOfAppt() {
        return timeOfAppt;
    }

    @Override
    public boolean equals(Object o) {
        boolean equal = true;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Appointment that = (Appointment) o;


        if (this.doctorId != that.doctorId) {
            equal = false;
            return equal;
        }

        if (this.patientId != that.patientId) {
            equal = false;
            return equal;
        }

        if (!this.dateOfAppt.equals(that.dateOfAppt)) {
            equal = false;
            return equal;
        }

        if (!this.timeOfAppt.equals(that.timeOfAppt)) {
            equal = false;
            return equal;
        }

        return equal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.dateOfAppt);
    }
}
