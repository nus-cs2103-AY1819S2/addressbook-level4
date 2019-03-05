package seedu.address.model.medicalhistory;

import seedu.address.model.person.Name;
import seedu.address.model.person.Patient;

/**
 * Represents a Medical History in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class MedicalHistory {

    // Identity field
    /*
    private Patient patient;
    private Doctor doctor;
    */
    private Name name;
    private Patient patient;
    private WriteUp writeUp;
    /*
    // Data field
    private Date time;
    */
    //Constructor
    public MedicalHistory(/*Doctor doctor, */Patient patient, Name name, WriteUp writeUp /* Date time*/) {
        /*
        this.patient = patient;
        this.doctor = doctor;
        this.writeup = note;
        this.time = time;
        */
        this.name = name;
        this.patient = patient;
        this.writeUp = writeUp;
    }

    public Name getName() {
        return this.name;
    }

    public WriteUp getWriteUp() {
        return this.writeUp;
    }
}

