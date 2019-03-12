package seedu.address.commons.exceptions;

/**
 * Signals that the operation will result in duplicate card data
 */
public class DuplicateCardDataException extends RuntimeException {
    public DuplicateCardDataException() {
        super("Operation would result in duplicate card data");
    }
}
