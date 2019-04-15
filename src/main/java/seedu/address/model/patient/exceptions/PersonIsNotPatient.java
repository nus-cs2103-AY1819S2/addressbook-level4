package seedu.address.model.patient.exceptions;

/**
 * Signals the operation that a person is not a patient instance.
 */
public class PersonIsNotPatient extends RuntimeException {
    public PersonIsNotPatient() {
        super("Person instance is not of Patient class. This should not happen.");
    }
}
