package seedu.address.model.prescription;

import java.util.Objects;

import seedu.address.model.person.Doctor;
import seedu.address.model.person.Patient;

/**
 * Represents a Prescription in the docX.
 */
public class Prescription {
    // Identity field
    private Patient patient;
    private Doctor doctor;
    private Medicine medicine;

    // Data field
    private Description description;

    //Constructor
    public Prescription (Patient patient, Doctor doctor, Medicine medicine, Description description) {

        this.patient = patient;
        this.doctor = doctor;
        this.medicine = medicine;
        this.description = description;

    }

    public Description getDescription() {
        return this.description;
    }

    public Medicine getMedicine() {
        return this.medicine;
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
        return otherPrescription.getDescription().equals(this.getDescription())
                && otherPrescription.getMedicine().getName().equals(this.getMedicine().getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctor, patient, description);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        /*
        sb.append("Doctor Name: ");
        sb.append(this.doctor.getName());
        sb.append(" Patient Name: ");
        sb.append(this.patient.getName());
        */
        sb.append(" Medicine name: ");
        sb.append(this.medicine.getName());
        sb.append(" Description: ");
        sb.append(this.description.toString());
        return sb.toString();
    }


}


