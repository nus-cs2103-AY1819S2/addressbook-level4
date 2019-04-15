package seedu.address.model.restaurant.categories;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.util.StringUtil;

/**
 * Represents a Restaurant's cuisine in the food diary.
 * Guarantees: immutable; is valid as declared in {@link #isValidCuisine(String)}
 */
public class Cuisine {

    public static final String MESSAGE_CONSTRAINTS =
            "Cuisines should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * Cuisine should consist of alphanumeric characters
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final List<String> SUGGESTED_CUISINES = new ArrayList<>(Arrays.asList("Thai", "Western", "Indian",
            "Chinese", "Japanese", "Italian"));

    public final String value;

    public Cuisine(String cuisine) {
        requireNonNull(cuisine);
        checkArgument(isValidCuisine(cuisine), MESSAGE_CONSTRAINTS);
        value = StringUtil.capitalize(cuisine);
    }

    /**
     * Returns true if a given string is a valid cuisine.
     */
    public static boolean isValidCuisine(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cuisine // instanceof handles nulls
                && value.equals(((Cuisine) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
