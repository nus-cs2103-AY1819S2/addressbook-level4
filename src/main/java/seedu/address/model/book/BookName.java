package seedu.address.model.book;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**

 * Represents a Book's name in the BookShelf.

 * Guarantees: immutable; is valid as declared in {@link #isValidBookName(String)}
 */
public class BookName {

    public static final String MESSAGE_CONSTRAINTS =
        "Book names should contains only alphanumeric characters, spaces, '*', ',', '.', '?', ''', '()' and '&'.\n"
        + "And it should not be blank or have more than 50 characters (space included).\n";

    /*
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum}\\*\\,\\.\\?\\'\\&\\(\\) ]*";
    public static final int MAX_LENGTH = 50;

    public final String fullName;

    /**
     * Constructs a {@code BookName}.
     *
     * @param name A valid name.
     */
    public BookName(String name) {
        requireNonNull(name);
        checkArgument(isValidBookName(name), MESSAGE_CONSTRAINTS);
        fullName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidBookName(String test) {
        return test.length() <= MAX_LENGTH && test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookName // instanceof handles nulls
                && fullName.equals(((BookName) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }

}
