package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGpa(String)}
 */
public class Gpa {


    public static final String MESSAGE_CONSTRAINTS =
            "Gpa values should only contain float numbers between 0.0 and 4.0 (American Grading System)";
    // public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code Gpa}.
     *
     * @param gpa A valid gpa value.
     */
    public Gpa(String gpa) {
        requireNonNull(gpa);
        checkArgument(isValidGpa(gpa), MESSAGE_CONSTRAINTS);
        value = correctForm(gpa);
    }

    /**
     * Converts a string of integer to a string of 1dp format
     */
    private String correctForm(String gpa) {
        if (!gpa.contains(".")) {
            gpa = gpa + ".0";
        }
        return gpa;
    }

    /**
     * Returns true if a given string is a valid gpa.
     */
    public static boolean isValidGpa(String test) {

        try {
            float value = Float.parseFloat(test.trim().toLowerCase());
            if (value < 0.0 || value > 4.0) {
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
                || (other instanceof Gpa // instanceof handles nulls
                && value.equals(((Gpa) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}

