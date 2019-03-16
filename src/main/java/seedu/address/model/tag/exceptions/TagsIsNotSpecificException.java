package seedu.address.model.tag.exceptions;

/**
 * Throws an exception to indicate unspecific Tag classes.
 */
public class TagsIsNotSpecificException extends RuntimeException {
    public TagsIsNotSpecificException() {
        super("Tag should be specified, Teeth or Status kind.");
    }
}
