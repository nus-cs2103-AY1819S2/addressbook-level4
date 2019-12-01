package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Objects;

import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;

/**
 * Represents an Appointment made by a patient.
 */
public class Appointment {

    private final AppointmentPatientId patientId;
    private final AppointmentDoctorId doctorId;
    private final AppointmentDate date;
    private final AppointmentTime time;
    private final AppointmentStatus appointmentStatus;
    private Patient patient;
    private Doctor doctor;

    /**
     * Constructs a new {@code Appointment}. New appointments default to ACTIVE status.
     *
     * @param patientId A valid patientId.
     * @param doctorId A valid doctorId.
     * @param date A valid appointment date
     * @param time A valid appointment time
     */
    public Appointment(AppointmentPatientId patientId, AppointmentDoctorId doctorId, AppointmentDate date,
                       AppointmentTime time) {

        this(patientId, doctorId, date, time, AppointmentStatus.ACTIVE);
        //checkArgument(isValidAppointment(appointment), MESSAGE_CONSTRAINTS);
    }

    /**
     * Constructs an {@code Appointment} with a stated status.
     *
     * @param patientId A valid patientId.
     * @param doctorId A valid doctorId.
     * @param date A valid appointment date
     * @param time A valid appointment time
     */
    public Appointment(AppointmentPatientId patientId, AppointmentDoctorId doctorId, AppointmentDate date,
                       AppointmentTime time, AppointmentStatus appointmentStatus) {
        /**
         * Every field must be present and not null.
         */
        requireAllNonNull(patientId, doctorId, date, time, appointmentStatus);
        //checkArgument(isValidAppointment(appointment), MESSAGE_CONSTRAINTS);
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.appointmentStatus = appointmentStatus;
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

    /**
     * Checks if an appointment is in the past compared to system time.
     *
     * @return true if the appointment is in the past.
     */
    public boolean isPast() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime appointmentDateTime = LocalDateTime.of(date.date, time.time);

        final boolean past = appointmentDateTime.isBefore(currentDateTime);
        return past;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        // subclasses of this class are equal to the superclass
        if (o == null || !(o instanceof Appointment)) {
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

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientId, doctorId, date, time);
    }
}
