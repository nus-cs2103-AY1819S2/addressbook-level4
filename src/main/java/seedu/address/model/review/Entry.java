package seedu.address.model.review;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Review Entry in the food diary.
 * Guarantees: immutable; name is valid as declared in {@link #isValidEntry(String)}
 */
public class Entry {

    public static final String MESSAGE_CONSTRAINTS = "Entries can take any values, and they should not be blank";
    /*
     * The first character of the entry must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String entry;

    /**
     * Constructs a {@code entry}.
     *
     * @param entry A valid entry.
     */
    public Entry(String entry) {
        requireNonNull(entry);
        checkArgument(isValidEntry(entry), MESSAGE_CONSTRAINTS);
        this.entry = entry;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidEntry(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return entry;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Entry // instanceof handles nulls
                && entry.equals(((Entry) other).entry)); // state check
    }

    @Override
    public int hashCode() {
        return entry.hashCode();
    }

}
