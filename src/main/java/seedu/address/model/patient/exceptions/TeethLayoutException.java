package seedu.address.model.patient.exceptions;

/**
 * Signals the operation that the chosen teeth layout is not recognised, and that no operations is done.
 */
public class TeethLayoutException extends RuntimeException {
    public TeethLayoutException() {
        super("Invalid teeth layout is selected. No teeth is built.");
    }
}
