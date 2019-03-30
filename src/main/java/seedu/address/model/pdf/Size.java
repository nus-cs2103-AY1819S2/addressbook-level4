package seedu.address.model.pdf;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Pdf's file size in the pdf book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSize(String)}
 */
public class Size {
    public static final String MESSAGE_CONSTRAINTS = "File size should be a non-negative number";

    public static final String VALIDATION_REGEX = "^\\d+$";

    private final String value;

    public Size(String size) {
        requireNonNull(size);
        checkArgument(isValidSize(size), MESSAGE_CONSTRAINTS);
        value = size;
    }

    /**
     * Returns true if a given string is a non-negative number.
     */
    public static boolean isValidSize(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Size: ").append(value).append("\n").toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Size // instanceof handles nulls
                && value.equals(((Size) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
