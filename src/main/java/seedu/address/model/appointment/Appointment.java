package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;

/**
 * Represents an Appointment made by a patient.
 */
public class Appointment {

    public static final String MESSAGE_CONSTRAINT_FUTURE = "Appointments can only be made in the future";

    private final AppointmentPatientId patientId;
    private final AppointmentDoctorId doctorId;
    private final AppointmentDate date;
    private final AppointmentTime time;
    private final AppointmentStatus appointmentStatus;
    private Patient patient;
    private Doctor doctor;

    /**
     * Constructs an {@code Appointment}.
     *
     * @param patientId A valid patientId.
     * @param doctorId A valid doctorId.
     * @param date A valid appointment date
     * @param time A valid appointment time
     */
    public Appointment(AppointmentPatientId patientId, AppointmentDoctorId doctorId, AppointmentDate date,
                       AppointmentTime time) {
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

    public AppointmentPatientId getPatientId() {
        return patientId;
    }

    public AppointmentDoctorId getDoctorId() {
        return doctorId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public AppointmentDate getDate() {
        return date;
    }

    public AppointmentTime getTime() {
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

        if (!this.doctorId.equals(that.doctorId)) {
            return false;
        }

        if (!this.patientId.equals(that.patientId)) {
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
        return Objects.hash(patientId, doctorId, date, time);
    }
}
