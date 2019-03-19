package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEducation(String)}
 */
public class Education {

    public static final String MESSAGE_CONSTRAINTS =
            "Education should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String university;

    /**
     * Constructs a {@code Education}.
     *
     * @param education A valid university.
     */
    public Education(String education) {
        requireNonNull(education);
        checkArgument(isValidEducation(education), MESSAGE_CONSTRAINTS);
        university = education;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidEducation(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return university;
    }

    //unsure if needed bc people can have the same university???
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Education // instanceof handles nulls
                && university.equals(((Education) other).university)); // state check
    }

    @Override
    public int hashCode() {
        return university.hashCode();
    }

}