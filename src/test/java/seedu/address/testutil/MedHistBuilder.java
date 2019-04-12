package seedu.address.testutil;

import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.ValidDate;
import seedu.address.model.medicalhistory.WriteUp;
import seedu.address.model.person.PersonId;

/**
 * A utility class to help with building Medical History objects.
 */
public class MedHistBuilder {

    public static final String DEFAULT_PATIENT_ID = "1";
    public static final String DEFAULT_DOCTOR_ID = "7";
    public static final String DEFAULT_DATE = "2019-03-03";
    public static final String DEFAULT_WRITEUP = "The patient got a high fever.";

    private PersonId patientId;
    private PersonId doctorId;
    private ValidDate date;
    private WriteUp writeUp;

    public MedHistBuilder() {
        patientId = new PersonId(DEFAULT_PATIENT_ID);
        doctorId = new PersonId(DEFAULT_DOCTOR_ID);
        date = new ValidDate(DEFAULT_DATE);
        writeUp = new WriteUp(DEFAULT_WRITEUP);
    }

    /**
     * Initializes the MedHistBuilder with the data of {@code medHistToCopy}.
     */
    public MedHistBuilder(MedicalHistory medHistToCopy) {
        patientId = medHistToCopy.getPatientId();
        doctorId = medHistToCopy.getDoctorId();
        date = medHistToCopy.getDate();
        writeUp = medHistToCopy.getWriteUp();
    }

    /**
     * Sets the {@code PatientId} of the {@code Medical History} that we are building.
     */
    public MedHistBuilder withPid(String patientId) {
        this.patientId = new PersonId(patientId);
        return this;
    }

    /**
     * Sets the {@code DoctorId} of the {@code Medical History} that we are building.
     */
    public MedHistBuilder withDid(String doctorId) {
        this.doctorId = new PersonId(doctorId);
        return this;
    }

    /**
     * Sets the {@code ValidDate} of the {@code Medical History} that we are building.
     */
    public MedHistBuilder withDate(String date) {
        this.date = new ValidDate(date);
        return this;
    }

    /**
     * Sets the {@code WriteUp} of the {@code Medical History} that we are building.
     */
    public MedHistBuilder withWriteUp(String writeUp) {
        this.writeUp = new WriteUp(writeUp);
        return this;
    }

    public MedicalHistory build() {
        return new MedicalHistory(patientId, doctorId, date, writeUp);
    }
}
