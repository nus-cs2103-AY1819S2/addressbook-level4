package seedu.address.model.apparel.exceptions;

/**
 * Signals that the operation will result in duplicate Apparels (Apparels are considered
 * duplicates if they have the same identity).
 */
public class DuplicateApparelException extends RuntimeException {
    public DuplicateApparelException() {
        super("Operation would result in duplicate persons");
    }
}
