package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Workload's hour number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidHour(String)}
 */
public class Hour {


    public static final String MESSAGE_CONSTRAINTS =
            "Hour should be a non-negative number to .5 degree of accuracy";
    public static final String VALIDATION_REGEX = "\\d{1,3}(\\.[05])?";
    public final String value;

    /**
     * Constructs a {@code Hour}.
     *
     * @param hour A valid hour number.
     */
    public Hour(String hour) {
        requireNonNull(hour);
        checkArgument(isValidHour(hour), MESSAGE_CONSTRAINTS);
        value = hour;
    }

    /**
     * Returns true if a given string is a valid hour number.
     */
    public static boolean isValidHour(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Hour // instanceof handles nulls
                && value.equals(((Hour) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
