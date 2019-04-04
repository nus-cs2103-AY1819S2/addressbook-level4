package seedu.address.model.moduletaken;

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
    public final Double hour;

    /**
     * Constructs a {@code Hour}.
     *
     * @param hour A valid hour number.
     */
    public Hour(String hour) {
        requireNonNull(hour);
        checkArgument(isValidHour(hour), MESSAGE_CONSTRAINTS);
        this.hour = Double.parseDouble(hour);
    }

    /**
     * Returns true if a given string is a valid hour number.
     */
    public static boolean isValidHour(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns if this number of hours is no more than another number of hours.
     */
    public boolean isWithin(Hour limit) {
        return this.hour <= limit.getHour();
    }

    @Override
    public String toString() {
        return hour.toString();
    }

    public double getHour() {
        return hour;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Hour // instanceof handles nulls
                && hour.equals(((Hour) other).hour)); // state check
    }

    @Override
    public int hashCode() {
        return hour.hashCode();
    }
}
