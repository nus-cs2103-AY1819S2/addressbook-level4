package seedu.finance.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.finance.commons.util.AppUtil.checkArgument;


/**
 * Represents an Entry's amount in the finance tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class Amount {

    public static final String MESSAGE_CONSTRAINTS = "Amount is a positive number, "
            + "with either 2 decimal place or none.\n"
            + "Amount must not be larger than $100 000 000.";

    public static final String AMOUNT_CONSTRAINTS = "Amount must not be larger than $100 000 000.";
    public static final String VALIDATION_REGEX = "(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$";

    public static final double MAX_AMOUNT = 100000000;

    private String value;

    /**
     * Constructs an {@code Amount}.
     *
     * @param amount A valid amount.
     */
    public Amount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        value = amount;
    }

    /**
     * Returns if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        if (test.matches(VALIDATION_REGEX) && Double.parseDouble(test) <= MAX_AMOUNT) {
            return Double.parseDouble(test) > 0;
        }
        return false;
    }

    public Double getValue() {
        return Double.parseDouble(value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.finance.model.record.Amount // instanceof handles nulls
                && value.equals(((seedu.finance.model.record.Amount) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return Double.valueOf(value).hashCode();
    }

}
