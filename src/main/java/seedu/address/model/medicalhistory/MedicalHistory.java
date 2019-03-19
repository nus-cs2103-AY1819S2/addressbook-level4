package seedu.address.model.medicalhistory;

import java.util.Objects;

import seedu.address.model.person.Doctor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Patient;


/**
 * Represents a Medical History in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MedicalHistory {

    // Identity field
    private Patient patient;
    private Name name;
    private Doctor doctor;

    // Data field
    private WriteUp writeUp;

    //Constructor
    public MedicalHistory(Patient patient, Doctor doctor, Name name, WriteUp writeUp) {
        // Doctor, Time, MedicalHistory Id are needed
        this.patient = patient;
        this.doctor = doctor;
        this.name = name;
        this.writeUp = writeUp;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public Name getName() {
        return this.name;
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
                && otherMedHist.getName().equals(getName())
                && (otherMedHist.getWriteUp().equals(getWriteUp()));
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
        return otherMedHist.getName().equals(getName())
                && otherMedHist.getWriteUp().equals(getWriteUp());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(patient, name, writeUp);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
                .append(getName())
                .append(" WriteUp: ")
                .append(getWriteUp());
        return builder.toString();
    }
}

