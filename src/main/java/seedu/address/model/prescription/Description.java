package seedu.address.model.prescription;

/**
 * Represents description of a prescription in the docX.
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS =
            "Description should be short";

    private String description;

    public Description(String description) {

        this.description = description;
    }

    @Override
    public String toString() {

        return this.description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        return true;
    }

    public String getDescription() {

        return description;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Description
                && this.description.equals(((Description) other).description);
    }

    @Override
    public int hashCode() {

        return description.hashCode();
    }
}
