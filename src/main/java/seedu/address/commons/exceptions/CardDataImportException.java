package seedu.address.commons.exceptions;

/**
 * Signals that the operation will result in duplicate file
 */
public class CardDataImportException extends RuntimeException {
    public CardDataImportException(String msg) {
        super(msg);
    }
}
