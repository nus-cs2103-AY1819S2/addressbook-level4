/* @@author siyingpoof */
package seedu.address.model.person.doctor;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Doctor's year(s) of experience in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidYear(String)}
 */
public class Year {

    public static final String MESSAGE_CONSTRAINTS =
            "Year of experience should only contain numbers, and it should between 0-100";
    public static final String VALIDATION_REGEX = "([0-9]|[1-8][0-9]|9[0-9]|100)"; // 1   -100
    public final String value;

    /**
     * Constructs a {@code Year}.
     *
     * @param years A valid years of experience number.
     */
    public Year(String years) {
        requireNonNull(years);
        checkArgument(isValidYear(years), MESSAGE_CONSTRAINTS);
        value = years;
    }

    /**
     * Returns true if a given string is a valid years of experience number.
     */
    public static boolean isValidYear(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Year // instanceof handles nulls
                && value.equals(((Year) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
