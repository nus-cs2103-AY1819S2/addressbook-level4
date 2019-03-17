package seedu.hms.model.customer;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.util.AppUtil.checkArgument;

/**
 * Represents a Customer's hms in the hms book.
 * Guarantees: immutable; is valid as declared in {@link #isValidhms(String)}
 */
public class Address {

    public static final String MESSAGE_CONSTRAINTS = "Addresses can take any values, and it should not be blank";

    /*
     * The first character of the hms must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code hms}.
     *
     * @param hms A valid hms.
     */
    public Address(String hms) {
        requireNonNull(hms);
        checkArgument(isValidAddress(hms), MESSAGE_CONSTRAINTS);
        value = hms;
    }

    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidAddress(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Address // instanceof handles nulls
            && value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
