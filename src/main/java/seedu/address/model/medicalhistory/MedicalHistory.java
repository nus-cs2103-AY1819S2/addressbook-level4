package seedu.address.model.medicalhistory;

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
    private String patientId;
    private String doctorId;
    private String medHistId;

    // Data field
    private Date date;
    private WriteUp writeUp;

    //Constructor
    public MedicalHistory(String patientId, String doctorId, Date date, WriteUp writeUp) {
        // Doctor, Time, MedicalHistory Id are needed
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

    public String getPatientId() {
        return this.patientId;
    }

    public String getDoctorId() {
        return this.doctorId;
    }

    public Date getDate() {
        return this.date;
    }

    public WriteUp getWriteUp() {
        return this.writeUp;
    }

    /**
     * Returns true if both persons of the same name
     * have at least one other identity field (phone number) that is the same.
     * This defines a weaker notion of equality between two persons.
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
        return otherMedHist.getMedHistId().equals(getMedHistId());
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

