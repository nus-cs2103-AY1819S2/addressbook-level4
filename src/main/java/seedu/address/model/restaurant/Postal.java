package seedu.address.model.restaurant;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Restaurant's postal code in the food diary.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostal(String)}
 */
public class Postal {


    public static final String MESSAGE_CONSTRAINTS =
            "Postal Code should only contain numbers, and it should 6 digits long";
    public static final String VALIDATION_REGEX = "\\d{6}";
    public final String value;

    /**
     * Constructs a {@code Postal}.
     *
     * @param postal A valid postal code.
     */
    public Postal(String postal) {
        requireNonNull(postal);
        checkArgument(isValidPostal(postal), MESSAGE_CONSTRAINTS);
        value = postal;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPostal(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Postal // instanceof handles nulls
                && value.equals(((Postal) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
