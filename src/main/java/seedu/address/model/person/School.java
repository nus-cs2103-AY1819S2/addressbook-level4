package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSchool(String)}
 */
public class School {

    public static final String MESSAGE_CONSTRAINTS = "Schools can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    public School(String school) {
        requireNonNull(school);
        checkArgument(isValidSchool(school), MESSAGE_CONSTRAINTS);
        value = school;
    }

    public static boolean isValidSchool(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof School // instanceof handles null
                && value.equals(((School) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
