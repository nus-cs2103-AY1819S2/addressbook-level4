package seedu.travel.model.place.exceptions;

/**
 * Signals that the operation will result in duplicate Places (Places are considered duplicates if they have the same
 * identity).
 */
public class DuplicatePlaceException extends RuntimeException {
    public DuplicatePlaceException() {
        super("Operation would result in duplicate places");
    }
}
