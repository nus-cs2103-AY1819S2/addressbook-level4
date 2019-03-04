package seedu.address.model.apparel;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Apparel's color in the apparel bank.
 */
public class Color {

    public static final String MESSAGE_CONSTRAINTS =
            "Colors should only be those that are listed in the list of accepted colors.\n"
                    + "Type `colors` to see all the accepted colors.";

    private ColorValue primary;

    /**
     * Constructs a {@code Color}.
     *
     * @param primary A valid primary color.
     */
    public Color(ColorValue primary) {
        requireNonNull(primary);
        checkArgument(ColorValue.isValidColor(primary), MESSAGE_CONSTRAINTS);

        this.primary = primary;
    }

    /**
     * Constructs a {@code Color}.
     *
     * @param primary A valid primary color.
     */
    public Color(String primary) {
        requireNonNull(primary);
        checkArgument(ColorValue.isValidColor(primary), MESSAGE_CONSTRAINTS);

        this.primary = ColorValue.valueOf(primary.toUpperCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Color // instanceof handles nulls
                && primary == (((Color) other).primary)); // state check
    }

    @Override
    public String toString() {
        return primary.name();
    }
}
