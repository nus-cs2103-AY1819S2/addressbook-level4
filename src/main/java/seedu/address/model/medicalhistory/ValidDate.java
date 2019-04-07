package seedu.address.model.medicalhistory;

import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDate;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

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
}
