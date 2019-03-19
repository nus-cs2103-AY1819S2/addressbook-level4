package seedu.address.model.restaurant.categories;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Restaurant's occasion in the food diary.
 * Guarantees: immutable; is valid as declared in {@link #isValidOccasion(String)}
 */
public class Occasion {

    public static final String MESSAGE_CONSTRAINTS =
            "Occasions should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * Occasion should consist of alphanumeric characters
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    public Occasion(String occasion) {
        requireNonNull(occasion);
        checkArgument(isValidOccasion(occasion), MESSAGE_CONSTRAINTS);
        value = occasion;
    }

    /**
     * Returns true if a given string is a valid occasion.
     */
    public static boolean isValidOccasion(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Occasion // instanceof handles nulls
                && value.equals(((Occasion) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
