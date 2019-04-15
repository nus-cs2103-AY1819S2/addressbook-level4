package seedu.address.model.prescription;

import java.util.Objects;

import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.doctor.Doctor;
import seedu.address.model.person.patient.Patient;

/**
 * Represents a Prescription in the docX.
 */
public class Prescription {
    // Identity field
    private Patient patient = null;
    private Doctor doctor = null;


    // Id field
    private PersonId patientId;
    private PersonId doctorId;

    // Data field
    private ValidDate date;
    private Description description;
    private Medicine medicine;

    //Constructor
    public Prescription(PersonId patientId, PersonId doctorId, ValidDate date,
                        Medicine medicine, Description description) {

        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.medicine = medicine;
        this.description = description;

    }

    public Description getDescription() {
        return this.description;
    }

    public Medicine getMedicine() {
        return this.medicine;
    }

    public PersonId getPatientId() {
        return this.patientId;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public PersonId getDoctorId() {
        return this.doctorId;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public ValidDate getDate() {
        return this.date;
    }

    /**
     * Returns true if both medicalhistory have the same identity and data fields.
     * This defines a stronger notion of equality between two medicalhistory.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Prescription == false) {
            return false;
        }

        Prescription otherPrescription = (Prescription) other;
        return otherPrescription.getPatientId().equals(this.getPatientId())
                && otherPrescription.getDoctorId().equals(this.getDoctorId())
                && otherPrescription.getDate().equals(getDate())
                && otherPrescription.getDescription().equals(this.getDescription())
                && otherPrescription.getMedicine().getName().equals(this.getMedicine().getName());
    }

    /**
     * Returns true if both prescriptions have the same medicine, patientId, doctorId and description
     */
    public boolean isSamePrescription(Prescription other) {
        if (other == this) {
            return true;
        }

        return other != null
                && other.getMedicine().getName().equals(this.getMedicine().getName())
                && other.getDoctorId().equals(this.getDoctorId())
                && other.getDate().equals(this.getDate())
                && other.getPatientId().equals(this.getPatientId())
                && other.getDescription().equals(this.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(doctor, patient, date, medicine, description);
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
        sb.append(" Patient ID: ");
        sb.append(getPatientId());
        sb.append(" Doctor ID: ");
        sb.append(getDoctorId());
        sb.append(" Medicine name: ");
        sb.append(this.medicine.getName());
        sb.append(" Date: ");
        sb.append(getDate());
        sb.append(" Description: ");
        sb.append(this.description.toString());
        return sb.toString();
    }


}


