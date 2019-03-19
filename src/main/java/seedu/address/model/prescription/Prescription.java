package seedu.address.model.prescription;

import java.util.Objects;

import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;

/**
 * Represents a Prescription in the address book.
 */
public class Prescription {
    // Identity field
    private Patient patient;
    private Doctor doctor;

    // Data field
    private Description description;

    //Constructor
    public Prescription (Patient patient, Doctor doctor, Description description) {

        this.patient = patient;
        this.doctor = doctor;
        this.description = description;
    }

    public Description getDescription() {
        return this.description;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }
    public Patient getPatient() {
        return this.patient;
    }

    /**
     * Returns true if both medicalHistory have the same identity and data fields.
     * This defines a stronger notion of equality between two medicalHistory.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Prescription == false) {
            return false;
        }

        Prescription otherPrescription = (Prescription) other;
        return otherPrescription.getDescription().equals(this.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctor, patient, description);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Doctor Name: ");
        sb.append(this.doctor.getName());
        sb.append(" Patient Name: ");
        sb.append(this.patient.getName());
        sb.append(" Description: ");
        sb.append(this.description.toString());
        return sb.toString();
    }


}


