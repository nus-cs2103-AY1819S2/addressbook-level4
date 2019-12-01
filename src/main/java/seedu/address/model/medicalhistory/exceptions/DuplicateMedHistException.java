package seedu.address.model.medicalhistory.exceptions;

/**
 * Signals that the operation will result in duplicate MedicalHistory.
 */
public class DuplicateMedHistException extends RuntimeException {
    public DuplicateMedHistException() {
        super("Operation would result in duplicate medical histories");
    }
}
