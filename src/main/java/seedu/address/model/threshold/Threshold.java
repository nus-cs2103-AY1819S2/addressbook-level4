package seedu.address.model.threshold;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the threshold of a Medicine or Batch predicate.
 * Guarantees: immutable; is valid as declared in {@link #isValidThreshold(String)}
 */
public class Threshold {
    public static final int MIN_THRESHOLD = 0;
    public static final String MESSAGE_CONSTRAINTS = "Threshold should only contain numbers. Min threshold: "
            + MIN_THRESHOLD;
    public static final String VALIDATION_REGEX = "\\d+";
    public final String value;

    /**
     * Constructs a {@code Threshold}.
     *
     * @param threshold A valid threshold.
     */
    public Threshold(String threshold) {
        requireNonNull(threshold);
        checkArgument(isValidThreshold(threshold), MESSAGE_CONSTRAINTS);
        this.value = threshold;
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidThreshold(String test) {
        try {
            return Integer.parseInt(test) >= MIN_THRESHOLD && test.matches(VALIDATION_REGEX);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns threshold as an int
     */
    public int getNumericValue() {
        return Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Threshold // instanceof handles nulls
                && value.equals(((Threshold) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
