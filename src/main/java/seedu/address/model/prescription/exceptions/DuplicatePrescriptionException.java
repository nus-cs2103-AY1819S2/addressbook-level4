package seedu.address.model.prescription.exceptions;

/**
 * Signals that the operation will result in duplicate MedicalHistory.
 */
public class DuplicatePrescriptionException extends RuntimeException {
    public DuplicatePrescriptionException() {
        super("Cannot add a prescription which already exists");
    }
}
