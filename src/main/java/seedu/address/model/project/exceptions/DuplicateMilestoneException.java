package seedu.address.model.project.exceptions;

/**
 * Signals that the operation will result in duplicate milestones (Milestones are considered duplicates if they have the
 * same name).
 */
public class DuplicateMilestoneException extends RuntimeException {
    public DuplicateMilestoneException() {
        super("Operation would result in duplicate milestones");
    }
}
