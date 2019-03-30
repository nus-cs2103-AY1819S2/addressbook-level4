package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's major in the club manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidMajor(String)}
 */
public class Major {

    public static final String MESSAGE_CONSTRAINTS =
            "Majors should only contain alphabets and spaces, and it should not be blank";

    /*
     * The first character of the major must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param major A valid major.
     */
    public Major(String major) {
        requireNonNull(major);
        checkArgument(isValidMajor(major), MESSAGE_CONSTRAINTS);
        this.value = major;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidMajor(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Major // instanceof handles nulls
                && value.equals(((Major) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
