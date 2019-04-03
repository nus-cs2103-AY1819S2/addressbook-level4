package seedu.address.model.project;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.text.DateFormat;
import java.text.ParseException;

/**
 * The A date object.
 */

public class ProjectDate {
    public static final String MESSAGE_CONSTRAINTS = "Deadlines should be in the format DD/MM/YYYY. User can also "
        + "choose to go for a flexible date input which supports the following: today, tomorrow, yesterday, "
        + "this/next/last month DAY_OF_MONTH & "
        + "this/next/last week DAY_OF_WEEK";

    /**
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */

    public static final String VALIDATION_REGEX = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    public final String date;

    public ProjectDate(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.date = date;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String input) {
        DateFormat format = Project.DATE_FORMAT;
        format.setLenient(false);
        try {
            format.parse(input);
        } catch (ParseException e) {
            return false;
        }
        return input.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a clone of this Deadline object.
     */
    public ProjectDate clone() {
        return new ProjectDate(this.date);
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProjectDate // instanceof handles nulls
                && date.equals(((ProjectDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
