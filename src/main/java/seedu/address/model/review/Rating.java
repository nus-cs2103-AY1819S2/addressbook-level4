package seedu.address.model.review;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DecimalFormat;

/**
 * Represents a Review's rating in the food diary.
 * Guarantees: immutable; is valid as declared in {@link #isValidRating(String)}
 */
public class Rating {

    public static final String MESSAGE_CONSTRAINTS =
            "Ratings should only contain digits or 1 decimal place numbers within the range 0-5, inclusive.";
    public static final String VALIDATION_REGEX = "(^0*[0-4](\\.[0-9])?$)|(5(\\.0)?$)";
    private static final DecimalFormat ONE_DP = new DecimalFormat("0.0");
    public final String value;

    /**
     * Constructs a {@code Rating}.
     *
     * @param rating A valid rating.
     */
    public Rating(String rating) {
        requireNonNull(rating);
        checkArgument(isValidRating(rating), MESSAGE_CONSTRAINTS);

        // To ensure that any existing leading 0s to positive ratings are removed
        float ratingValue = Float.parseFloat(rating);
        value = ONE_DP.format(ratingValue);
    }

    /**
     * Returns true if a given string is a valid rating.
     */
    public static boolean isValidRating(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a float containing the value of the rating.
     */
    public float toFloat() {
        return Float.parseFloat(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Rating // instanceof handles nulls
                && value.equals(((Rating) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
