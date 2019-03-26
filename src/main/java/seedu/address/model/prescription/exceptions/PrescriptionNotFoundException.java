package seedu.address.model.prescription.exceptions;

/**
 * Signals that the specified prescription cannot be found in the ArrayList.
 */
public class PrescriptionNotFoundException extends RuntimeException {
    public PrescriptionNotFoundException() {
        super("The prescription to remove does not exist");
    }
}