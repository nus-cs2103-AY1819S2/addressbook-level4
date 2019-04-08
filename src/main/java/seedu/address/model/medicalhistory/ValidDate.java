package seedu.address.model.medicalhistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

/**
 * Represents date of a medical history in the docX.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(LocalDate)}
 */
public class ValidDate {

    public static final String MESSAGE_CONSTRAINTS =
            "A medical history must happen in the past. Date must be a past date or today.";
    public final LocalDate value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid write up.
     */
    public ValidDate(LocalDate date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(LocalDate date) {
        if (date.compareTo(LocalDate.now()) <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ValidDate // instanceof handles nulls
                && value.equals(((ValidDate) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
