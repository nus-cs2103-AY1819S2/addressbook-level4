package seedu.address.model.datetime;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a date.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS = "Date should only contain exactly 8 numbers";

    public static final String VALIDATION_REGEX = "^((([0-2][0-9]|3[0,1])(01|03|05|07|08|10|12))|"
        + "(([0-2][0-9]|30)(04|06|09|11))|"
        + "(([0-1][0-9]|2[0-9])(02)))(\\d{4})$";

    public final String storedDate;

    /**
     * Constructs a {@code Date}
     *
     * @param date A valid date
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        storedDate = date;
    }

    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Date // instanceof handles nulls
            && storedDate.equals(((Date) other).storedDate)); // state check
    }

    @Override
    public int hashCode() {
        return storedDate.hashCode();
    }
}
