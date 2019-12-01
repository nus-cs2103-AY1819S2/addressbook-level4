package seedu.address.model.medicalhistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Represents date of a medical history in the docX.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class ValidDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Date must be a valid past date or today existing in the calendar. The format must be YYYY-MM-DD";
    public final LocalDate date;

    /**
     * Constructs a {@code Date}.
     *
     * @param dateString A valid write up.
     */
    public ValidDate(String dateString) {
        requireNonNull(dateString);
        checkArgument(isValidDate(dateString), MESSAGE_CONSTRAINTS);
        date = LocalDate.parse(dateString);
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String dateString) {
        LocalDate date;
        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            return false;
        }

        if (date.compareTo(LocalDate.now()) <= 0) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ValidDate // instanceof handles nulls
                && date.equals(((ValidDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
