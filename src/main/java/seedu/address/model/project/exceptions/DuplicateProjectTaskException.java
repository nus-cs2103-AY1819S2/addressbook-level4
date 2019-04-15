package seedu.address.model.project.exceptions;

/**
 * Signals that the operation will result in duplicate project task (Project tasks are considered duplicates if they
 * have the same task name).
 */
public class DuplicateProjectTaskException extends RuntimeException {
    public DuplicateProjectTaskException() {
        super("Operation would result in duplicate project task");
    }
}
