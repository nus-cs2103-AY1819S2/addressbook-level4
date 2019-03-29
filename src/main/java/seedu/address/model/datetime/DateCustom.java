package seedu.address.model.datetime;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a date for tasks.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class DateCustom implements DateBuilder {
    public static final String MESSAGE_CONSTRAINTS = "Date should not be before today's date, End Date should not"
                                                   + " be before Start Date and a valid date should"
                                                   + " be in the form of dd-mm-yyyy";

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
        return test.matches(VALIDATION_REGEX);
    }

    /**
     *  Returns false if the given date is before the current date
     * @param test the date to be tested
     */
    public static boolean isDateBeforeToday(String test) {
        String currentDateString = LocalDate.now().format(DateTimeFormatter.ofPattern(getFormat()));
        return dateCompare(test, currentDateString);
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

    public LocalDate getDate() {
        return LocalDate.parse(storedDate,DATE_FORMATTER);
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
