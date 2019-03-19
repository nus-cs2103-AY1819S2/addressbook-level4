package seedu.address.testutil;

import seedu.address.model.PatientBook;
import seedu.address.model.person.patient.Patient;

/**
 * Utility class for building PatientBook objects for testing.
 */
public class PatientBookBuilder {

    private PatientBook patientBook;

    public PatientBookBuilder() {
        this.patientBook = new PatientBook();
    }

    public PatientBookBuilder(PatientBook patientBook) {
        this.patientBook = patientBook;
    }

    /**
     * Adds a new {@code Patient} to the {@code PatientBook} that we are building.
     */
    public PatientBookBuilder withPatient(Patient patient) {
        this.patientBook.addPatient(patient);
        return this;
    }

    public PatientBook build() {
        return this.patientBook;
    }
}
