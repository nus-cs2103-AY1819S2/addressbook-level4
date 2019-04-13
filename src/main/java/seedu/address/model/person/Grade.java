package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's grade in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGrade(String)}
 */
public class Grade {


    public static final String MESSAGE_CONSTRAINTS =
            "Grade should only contain positive numbers equals to or less than 5, and must be in exactly 2 decimal places";
    public static final String VALIDATION_REGEX = "[0-4]" + "." + "\\d{2}";
    public static final String VALIDATION_REGEX_FULL = "5.00";
    public final String value;

    /**
     * Constructs a {@code Grade}.
     *
     * @param grade A valid grade.
     */

    public Grade(String grade) {
        requireNonNull(grade);
        checkArgument(isValidGrade(grade), MESSAGE_CONSTRAINTS);
        value = grade;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidGrade(String test) {
        return test.matches(VALIDATION_REGEX) || test.matches(VALIDATION_REGEX_FULL);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Grade // instanceof handles nulls
                && value.equals(((Grade) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
