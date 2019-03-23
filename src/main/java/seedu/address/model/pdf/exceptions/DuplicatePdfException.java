package seedu.address.model.pdf.exceptions;

/**
 * Signals that the operation will result in duplicate Persons (Persons are considered duplicates if they have the same
 * identity).
 */
public class DuplicatePdfException extends RuntimeException {
    public DuplicatePdfException() {
        super("Operation would result in duplicate pdf");
    }
}
