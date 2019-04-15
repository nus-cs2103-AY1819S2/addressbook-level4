package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Author's name in the book shelf.
 * Guarantees: immutable; is valid as declared in {@link #isValidAuthor(String)}
 */
public class Author {

    public static final String MESSAGE_CONSTRAINTS =
            "Author names should only contain alphanumeric characters and spaces, and it should not be blank "
            + "or longer than 50 characters";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final int MAX_LENGTH = 50;

    public final String fullName;

    /**
     * Constructs a {@code Author}.
     *
     * @param name A valid name.
     */
    public Author(String name) {
        requireNonNull(name);
        checkArgument(isValidAuthor(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidAuthor(String test) {
        return test.length() <= MAX_LENGTH && test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Author // instanceof handles nulls
                && fullName.equals(((Author) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
