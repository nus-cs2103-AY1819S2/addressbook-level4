package seedu.equipment.model.equipment;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


/**
 * Represents a Equipment's due date in the equipment book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date implements Comparable<Date> {

    public static final String MESSAGE_CONSTRAINTS =
            "Next preventive maintenance due date should only contain alphanumeric characters and spaces, "
                    + "and it should not be blank" + "\n" + "It should be in a format [date] [month] [year].";


    /*
     * The first character of the preventive maintenance date must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     *
     */
    public static final DateFormat VALID_DATE_FORMAT = new SimpleDateFormat("dd MMMM yyyy");

    public final String value;

    /**
     * Constructs an {@code Date}.
     *
     * @param date A valid date equipment.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Returns if a given string is a valid date.
     * The date must be a valid date, e.g: 32 February 2019 is not accepted.
     */
    public static boolean isValidDate(String test) {
        VALID_DATE_FORMAT.setLenient(false);
        try {
            VALID_DATE_FORMAT.parse(test);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(Date otherDate) {
        return value.compareTo(otherDate.value);
    }

}
