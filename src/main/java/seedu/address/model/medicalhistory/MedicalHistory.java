package seedu.address.model.medicalhistory;

import java.time.LocalDate;
import java.util.Objects;

import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;


/**
 * Represents a Medical History in the docX.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MedicalHistory {

    // Identity field
    private Patient patient = null;
    private Doctor doctor = null;

    //Current id field, will be modified later
    private int patientId;
    private int doctorId;
    private String medHistId;

    // Data field
    private ValidDate date;
    private WriteUp writeUp;

    //Constructor
    public MedicalHistory(int patientId, int doctorId, ValidDate date, WriteUp writeUp) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.writeUp = writeUp;
        this.medHistId = patientId + "/" + doctorId + "/" + date;

    }

    public String getMedHistId() {
        return this.medHistId;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public int getPatientId() {
        return this.patientId;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getDoctorId() {
        return this.doctorId;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return this.date.value;
    }

    public WriteUp getWriteUp() {
        return this.writeUp;
    }

    /**
     * Returns true if both medical histories of the same id
     * This defines a weaker notion of equality between two medical histories.
     */
    public boolean isSameMedHist(MedicalHistory otherMedHist) {
        if (otherMedHist == this) {
            return true;
        }

        return otherMedHist != null
                && otherMedHist.getMedHistId().equals(getMedHistId());
    }

    /**
     * Returns true if both medicalHistory have the same identity and data fields.
     * This defines a stronger notion of equality between two medicalHistory.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MedicalHistory)) {
            return false;
        }

        MedicalHistory otherMedHist = (MedicalHistory) other;

        return (otherMedHist.getPatientId() == getPatientId())
                && (otherMedHist.getDoctorId() == getDoctorId())
                && otherMedHist.getDate().equals(getDate())
                && otherMedHist.getWriteUp().equals(getWriteUp());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(medHistId);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Medical History ID: ")
                .append(getMedHistId())
                .append("Patient ID: ")
                .append(getPatientId())
                .append("Doctor ID: ")
                .append(getDoctorId())
                .append("Date: ")
                .append(getDate())
                .append(" WriteUp: ")
                .append(getWriteUp());
        return builder.toString();
    }
}

