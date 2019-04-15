package seedu.address.model.project.exceptions;

/**
 * Signals that the operation will result in duplicate user story (User stories are considered duplicates if they have
 * the same user, same function and same benefit).
 */
public class DuplicateUserStoryException extends RuntimeException {
    public DuplicateUserStoryException() {
        super("Operation would result in duplicate user story");
    }
}
