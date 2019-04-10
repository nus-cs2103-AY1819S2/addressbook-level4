package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate filter (Filters are considered duplicates if they have the same
 * filter name).
 */
public class DuplicateFilterException extends RuntimeException {
    public DuplicateFilterException() {
        super("Operation would result in duplicate filter");
    }
}
