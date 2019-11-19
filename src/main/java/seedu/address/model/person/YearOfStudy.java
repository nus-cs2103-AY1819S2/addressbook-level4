package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's yearOfStudy in the club manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidYearOfStudy(String)}
 */
public class YearOfStudy {

    public static final String MESSAGE_CONSTRAINTS =
            "YearOfStudy should only contain a single digit between 1 to 6 and it should not be blank";

    /*
     * The first character of the yearOfStudy must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[[1-6]{1}]";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param yearOfStudy A valid yearOfStudy.
     */
    public YearOfStudy(String yearOfStudy) {
        requireNonNull(yearOfStudy);
        checkArgument(isValidYearOfStudy(yearOfStudy), MESSAGE_CONSTRAINTS);
        this.value = yearOfStudy;
    }

    /**
     * Returns true if a given string is a valid yearOfStudy.
     */
    public static boolean isValidYearOfStudy(String yearOfStudy) {

        if ((!yearOfStudy.matches(VALIDATION_REGEX))) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "Year " + value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof YearOfStudy // instanceof handles nulls
                && value.equals(((YearOfStudy) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
