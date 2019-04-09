package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate doctors
 * (Doctors are considered duplicates if they have the same
 * identity).
 */
public class DuplicateDoctorException extends DuplicatePersonException {
    public DuplicateDoctorException() {
        super("Operation would result in duplicate doctors");
    }
}
