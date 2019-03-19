package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Calendar;

/**
 * Year of the stored statistics (mostly for calculation for total revenue).
 * Year has to be 4 digits and must not be more than the current year.
 */
public class Year {

    public static final String MESSAGE_CONSTRAINTS =
            "Year should be in the format <four digit integer>, it should not be blank or larger than current year";

    /*
     * The first character of the item code must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[0-9][0-9]{0,1}[0-9]{0,1}[0-9]{0,1}";

    private static boolean isInvalid = false;

    public final String year;


    /**
     * Constructs a {@code Year}.
     *
     * @param year A valid code.
     */
    public Year(String year) {
        requireNonNull(year);
        checkArgument(isValidYear(year), MESSAGE_CONSTRAINTS);
        this.year = year;
    }

    /**
     * Returns true if a given string is a valid code.
     */
    public static boolean isValidYear(String test) {
        requireNonNull(test);
        Calendar calendar = Calendar.getInstance();
        if (Integer.parseInt(test) > calendar.get(Calendar.YEAR)) {
            return isInvalid;
        } else if (Integer.parseInt(test) < 1) {
            return isInvalid;
        }
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return year;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Year // instanceof handles nulls
                && year.equals(((Year) other).year)); // state check
    }

    @Override
    public int hashCode() {
        return year.hashCode();
    }

}
