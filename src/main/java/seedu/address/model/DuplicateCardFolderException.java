package seedu.address.model;

/**
 * Signals that the operation will result in duplicate Cards (Cards are considered duplicates if they have the same
 * identity).
 */
public class DuplicateCardFolderException extends RuntimeException {
    public DuplicateCardFolderException() {
        super("Operation would result in duplicate card folders");
    }
}
