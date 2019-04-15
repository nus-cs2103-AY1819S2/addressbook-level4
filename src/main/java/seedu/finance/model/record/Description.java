package seedu.finance.model.record;

import static java.util.Objects.requireNonNull;

/**
 * Represents a record's description in finance tracker
 * Guarantees: immutable; is always valid
 */
public class Description {
    public static final String MESSAGE_CONSTRAINTS =
            "Description should not be longer than 40 characters.";

    public final String value;

    public Description(String description) {
        requireNonNull(description);
        this.value = description;
    }

    /**
     * Returns true if a given string is a valid description.
     */
    public static boolean isValidDescription(String test) {
        if (test.length() > 40) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof Description
                && value.equals(((Description) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
