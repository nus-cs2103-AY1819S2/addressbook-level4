package seedu.address.model.book.exceptions;

/**
 * Signals that the operation will result in duplicate Reviews
 */
public class DuplicateReviewException extends RuntimeException {
    public DuplicateReviewException() {
        super("Operation would result in duplicate reviews");
    }
}
