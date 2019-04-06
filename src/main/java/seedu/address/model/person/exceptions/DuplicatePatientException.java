package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate patients
 * (Patients are considered duplicates if they have the same
 * identity).
 */
public class DuplicatePatientException extends DuplicatePersonException {
    public DuplicatePatientException() {
        super("Operation would result in duplicate patients");
    }
}
