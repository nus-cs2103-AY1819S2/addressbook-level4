package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGpa(String)}
 */
public class GPA {


    public static final String MESSAGE_CONSTRAINTS =
            "GPA values should only contain numbers, and it should be at least 1 digits long";
    // public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code GPA}.
     *
     * @param gpa A valid gpa value.
     */
    public GPA(String gpa) {
        requireNonNull(gpa);
        checkArgument(isValidGpa(gpa), MESSAGE_CONSTRAINTS);
        value = gpa;
    }

    /**
     * Returns true if a given string is a valid gpa.
     */
    public static boolean isValidGpa(String test) {

        try {
            float value = Float.parseFloat(test.trim().toLowerCase());
            if(value < 0.0 || value > 4.0) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GPA // instanceof handles nulls
                && value.equals(((GPA) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

