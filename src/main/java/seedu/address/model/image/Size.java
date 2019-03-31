/* @@author Carrein */
package seedu.address.model.image;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Image's size in FomoFoto's Album.
 * Guarantees: immutable; is valid as declared in {@link #isValidSize(String)}
 */
public class Size {

    public static final String MESSAGE_CONSTRAINTS =
            "Size should not be less than 0 or blank";
    public final String value;

    /**
     * Constructs a {@code Size}.
     *
     * @param size A valid height.
     */
    public Size(String size) {
        requireNonNull(size);
        checkArgument(isValidSize(size), MESSAGE_CONSTRAINTS);
        value = size;
    }

    /**
     * Returns true if a given string is a valid height.
     */
    public static boolean isValidSize(String test) {
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
                || (other instanceof Size // instanceof handles nulls
                && value.equals(((Size) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
