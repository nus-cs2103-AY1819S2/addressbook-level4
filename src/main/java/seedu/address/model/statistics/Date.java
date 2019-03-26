package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Date of requested revenue from the command argument.
 * Date has to exists in the calendar
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Day should be in the format <double digit integer>, it should not be blank and should be a valid day";

    /*
     * The first character of the item code must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX =
            "[0-9][0-9]{0,1}[.][0-9][0-9]{0,1}[.][0-9][0-9]{0,1}[0-9]{0,1}[0-9]{0,1}";

    private static boolean isInvalid = false;

    public final String date;
    private final Day day;
    private final Month month;
    private final Year year;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid code.
     */
    public Date(String date) {
        requireNonNull(date);
        String[] data = date.split("\\.", 3);
        this.day = new Day(data[0]);
        this.month = new Month(data[1]);
        this.year = new Year(data[2]);
        this.date = new String(this.day + "." + this.month + this.year);
        checkArgument(isValidDate(date, day, month, year), MESSAGE_CONSTRAINTS);
    }

    /**
     * Returns true if a given string is a valid code.
     */
    public static boolean isValidDate(String test, Day day, Month month, Year year) {
        requireAllNonNull(day, month, year);
        if (Integer.parseInt(month.toString()) == 2) {
            if (Year.isLeapYear(year.toString()) && Integer.parseInt(day.toString()) > 29) {
                return isInvalid;
            } else if (!Year.isLeapYear(year.toString()) && Integer.parseInt(day.toString()) > 28) {
                return isInvalid;
            }
        } else if (Integer.parseInt(month.toString()) == 4 || Integer.parseInt(month.toString()) == 6
                || Integer.parseInt(month.toString()) == 9 || Integer.parseInt(month.toString()) == 11
                && Integer.parseInt(day.toString()) > 30) {
            return isInvalid;
        }
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && date.equals(((Date) other).date) && day.equals(((Date) other).day)
                && month.equals(((Date) other).month) && year.equals(((Date) other).year)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
