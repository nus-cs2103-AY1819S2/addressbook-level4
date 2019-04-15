package seedu.address.model.project.exceptions;

/**
 * Signals that the operation will result in duplicate projects (Projects are considered duplicates if they have the
 * same name).
 */
public class DuplicateProjectException extends RuntimeException {
    public DuplicateProjectException() {
        super("Operation would result in duplicate projects");
    }
}
