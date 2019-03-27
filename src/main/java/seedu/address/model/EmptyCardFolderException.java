package seedu.address.model;

/**
 * Signals that the the current card folder is empty.
 */
public class EmptyCardFolderException extends RuntimeException {
    public EmptyCardFolderException() {
        super("This card folder is empty");
    }
}
