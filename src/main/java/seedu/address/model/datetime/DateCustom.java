package seedu.address.model.datetime;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;

/**
 * Represents a date for tasks.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateCustom implements DateBuilder, Comparable<DateCustom> {

    public static final String MESSAGE_CONSTRAINTS = "Date given should be a valid date "
                                                    + "and should be in the format dd-mm-yyyy\n"
                                                    + "Example date: 31-03-2019";

    public static final String MESSAGE_CONSTRAINTS_START_DATE = " Start Date should be a valid date "
                                                               + "and should be in the format dd-mm-yyyy. "
                                                               + "You can also use the keyword today to use the "
                                                               + "current date\n Example date: 31-03-2019";

    public static final String MESSAGE_CONSTRAINTS_END_DATE = " End Date should be a valid date "
                                                                + "and should be in the format dd-mm-yyyy\n"
                                                                + "You can also use the keyword today to use the "
                                                                + "current date\n Example date: 31-03-2019";

    private final String storedDate;

    /**
     * Constructs a {@code DateCustom}
     *
     * @param date A valid date
     */
    public DateCustom(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        storedDate = date;
    }


    public static String getFormat() {
        return DATE_FORMAT;
    }

    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX) && isValidFeb29(test);
    }

    /**
     * Returns true if the two dates are the same
     * @param date
     */
    public boolean isSameDate(String date) {
        LocalDate firstDate = LocalDate.parse(storedDate, DATE_FORMATTER);
        LocalDate secondDate = LocalDate.parse(date, DATE_FORMATTER);
        return (firstDate.compareTo(secondDate) == 0);
    }
    public boolean isToday() {
        return !dateCompare(this.toString(), LocalDate.now().format(DATE_FORMATTER));
    }

    public static String getToday() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    public LocalDate getDate() {
        return LocalDate.parse(storedDate, DATE_FORMATTER);
    }

    /**
     *  Returns true if the first date given is before the second date given
     * @param date1 the first date to compare with the second date
     * @param date2 the second date
     * @return true if first date is before, false otherwise.
     */
    public static boolean dateCompare(String date1, String date2) {
        LocalDate firstDate = LocalDate.parse(date1, DATE_FORMATTER);
        LocalDate secondDate = LocalDate.parse(date2, DATE_FORMATTER);
        return firstDate.isBefore(secondDate);
    }

    /**
     * Checks for cases where 29 February is given. Returns true if its on a leap year.
     * @param test
     */
    private static boolean isValidFeb29(String test) {
        String[] date = test.split("-");
        if (Integer.valueOf(date[0]) == 29 && Integer.valueOf(date[1]) == 2) {
            LocalDate year = LocalDate.parse(test, DATE_FORMATTER);
            return year.isLeapYear();
        }
        return true;
    }

    @Override
    public int compareTo(DateCustom d) {
        if (isSameDate(d.storedDate)) {
            return 0;
        }
        return dateCompare(storedDate, d.storedDate) ? -1 : 1;
    }

    @Override
    public String toString() {
        return storedDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DateCustom // instanceof handles nulls
            && storedDate.equals(((DateCustom) other).storedDate)); // state check
    }

    @Override
    public int hashCode() {
        return storedDate.hashCode();
    }
}
