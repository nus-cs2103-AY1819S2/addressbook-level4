package seedu.address.model.util;

/**
 * Description of anything in the Pocket Project. A string that has no limitations as to what it can include.
 */

public abstract class Description {

    public static final String MESSAGE_CONSTRAINTS = "Description should not be empty or contain only spaces";

    /**
     * Method for checking if given string is valid
     */
    public static boolean isValidDescription(String description) {
        return !description.trim().isEmpty();
    }
}
