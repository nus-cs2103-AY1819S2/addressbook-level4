package seedu.address.model.restaurant.categories;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a Restaurant's price range in the food diary.
 * Guarantees: immutable; is valid as declared in {@link #isValidPriceRange(String)}
 */
public class PriceRange {
    public static final String MESSAGE_CONSTRAINTS =
            "Prices should only contain '$' characters to a maximum of 5, and it should not be blank";

    /*
     * PriceRange should consist of '$' characters, up to a maximum of 5 characters
     */
    public static final String VALIDATION_REGEX = "(\\$){1,5}";

    public static final List<String> SUGGESTED_PRICE_RANGES = new ArrayList<>(Arrays.asList("$", "$$", "$$$", "$$$$",
            "$$$$$"));

    public final String value;

    public PriceRange(String priceRange) {
        requireNonNull(priceRange);
        checkArgument(isValidPriceRange(priceRange), MESSAGE_CONSTRAINTS);
        value = priceRange;
    }

    /**
     * Returns true if a given string is a valid occasion.
     */
    public static boolean isValidPriceRange(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PriceRange // instanceof handles nulls
                && value.equals(((PriceRange) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
