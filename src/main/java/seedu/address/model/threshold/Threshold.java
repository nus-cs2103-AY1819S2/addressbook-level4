package seedu.address.model.threshold;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.util.warning.WarningPanelPredicateType;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Quantity;

/**
 * Represents the threshold of a Medicine or Batch predicate.
 * Guarantees: immutable; is valid as declared in {@link #isValidThreshold(String, WarningPanelPredicateType)}
 */
public class Threshold {
    public static final int MIN_THRESHOLD = 0;
    public static final int MAX_QUANTITY_THRESHOLD = Quantity.MAX_QUANTITY + 1;
    public static final int MAX_EXPIRY_THRESHOLD = Expiry.MAX_DAYS_TO_EXPIRY + 1;
    public static final String MESSAGE_CONSTRAINTS =
            "Threshold should only contain positive integers.\n"
            + "Maximum threshold for expiry: " + MAX_EXPIRY_THRESHOLD + "\n"
            + "Maximum threshold for quantity: " + MAX_QUANTITY_THRESHOLD;
    public static final String VALIDATION_REGEX = "\\d+";
    public final String value;

    /**
     * Constructs a {@code Threshold}.
     *
     * @param threshold A valid threshold.
     */
    public Threshold(String threshold, WarningPanelPredicateType type) {
        requireNonNull(threshold);
        checkArgument(isValidThreshold(threshold, type), MESSAGE_CONSTRAINTS);
        this.value = threshold;
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidThreshold(String test, WarningPanelPredicateType type) {
        try {
            return test.matches(VALIDATION_REGEX)
                    && Integer.parseInt(test) >= MIN_THRESHOLD
                    && Integer.parseInt(test) <= (type.equals(WarningPanelPredicateType.EXPIRY)
                        ? MAX_EXPIRY_THRESHOLD : MAX_QUANTITY_THRESHOLD);
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
