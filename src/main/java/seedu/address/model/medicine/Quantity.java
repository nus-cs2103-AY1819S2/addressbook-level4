package seedu.address.model.medicine;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the quantity of a Medicine in the inventory.
 * Guarantees: immutable; is valid as declared in {@link #isValidQuantity(String)}
 */
public class Quantity implements Comparable<Quantity> {

    public static final int MAX_QUANTITY = 1000000000;
    public static final int MIN_QUANTITY = 0;
    public static final String MESSAGE_CONSTRAINTS = "Quantity should only contain positive whole numbers. "
            + "Max Quantity is " + MAX_QUANTITY;
    public static final String VALIDATION_REGEX = "\\d+";
    private final int value;

    /**
     * Constructs a {@code Quantity}.
     *
     * @param quantity A valid quantity.
     */
    public Quantity(String quantity) {
        requireNonNull(quantity);
        checkArgument(isValidQuantity(quantity), MESSAGE_CONSTRAINTS);

        value = Integer.parseInt(quantity);
    }

    /**
     * Returns true if a given string is a valid quantity.
     */
    public static boolean isValidQuantity(String test) {
        try {
            return Integer.parseInt(test) <= MAX_QUANTITY
                    && Integer.parseInt(test) >= MIN_QUANTITY
                    && test.matches(VALIDATION_REGEX);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns quantity as an int
     */
    public int getNumericValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Quantity // instanceof handles nulls
                && value == ((Quantity) other).value); // state check
    }

    @Override
    public int compareTo(Quantity other) {
        return Integer.compare(getNumericValue(), other.getNumericValue());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
