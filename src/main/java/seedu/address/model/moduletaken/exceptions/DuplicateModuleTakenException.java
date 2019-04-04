package seedu.address.model.moduletaken.exceptions;

/**
 * Signals that the operation will result in duplicate ModulesTaken (ModulesTaken are considered
 * duplicates if they have the same ModuleInfoCode).
 */
public class DuplicateModuleTakenException extends RuntimeException {
    public DuplicateModuleTakenException() {
        super("Operation would result in duplicate modules taken");
    }
}
