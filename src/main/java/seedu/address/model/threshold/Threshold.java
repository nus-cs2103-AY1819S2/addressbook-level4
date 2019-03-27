package seedu.address.model.threshold;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the quantity of a Medicine in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
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
        checkArgument(isValidQuantity(threshold), MESSAGE_CONSTRAINTS);
        this.value = threshold;
    }

    /**
     * Constructs a {@code Threshold}.
     *
     * @param threshold A valid threshold.
     */
    public Threshold(Integer threshold) {
        requireNonNull(threshold.toString());
        checkArgument(isValidQuantity(threshold.toString()), MESSAGE_CONSTRAINTS);
        this.value = threshold.toString();
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidQuantity(String test) {
        try {
            return Integer.parseInt(test) >= MIN_THRESHOLD && test.matches(VALIDATION_REGEX);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns quantity as an int
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
