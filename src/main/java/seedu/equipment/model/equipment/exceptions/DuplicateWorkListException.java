package seedu.equipment.model.equipment.exceptions;

/**
 * Signals that the operation will result in duplicate WorkLists (WorkLists are considered duplicates
 * if they have the same identity).
 */
public class DuplicateWorkListException extends RuntimeException {
    public DuplicateWorkListException() {
        super("Operation would result in duplicate WorkLists");
    }
}
