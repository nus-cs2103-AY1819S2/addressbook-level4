package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Review's title.
 * Guarantees: immutable; is valid as declared in {@link #isValidReviewTitle(String)}
 */
public class ReviewTitle {

    public static final String MESSAGE_CONSTRAINTS =
            "Review Titles should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs a {@code ReviewTitle}.
     *
     * @param name A valid name.
     */
    public ReviewTitle(String name) {
        requireNonNull(name);
        checkArgument(isValidReviewTitle(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidReviewTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReviewTitle // instanceof handles nulls
                && fullName.equals(((ReviewTitle) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
