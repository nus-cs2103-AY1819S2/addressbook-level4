/* @@author Carrein */
package seedu.address.model.image;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Image's width in FomoFoto's Album.
 * Guarantees: immutable; is valid as declared in {@link #isValidWidth(String)}
 */
public class Width {

    public static final String MESSAGE_CONSTRAINTS =
            "Width should not be zero or blank";
    public final String value;

    /**
     * Constructs a {@code Width}.
     *
     * @param width A valid width.
     */
    public Width(String width) {
        requireNonNull(width);
        checkArgument(isValidWidth(width), MESSAGE_CONSTRAINTS);
        value = width;
    }

    /**
     * Returns true if a given string is a valid width.
     */
    public static boolean isValidWidth(String test) {
        boolean check = Integer.parseInt(test) <= 0 ? false : true;
        return check;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Width // instanceof handles nulls
                && value.equals(((Width) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
