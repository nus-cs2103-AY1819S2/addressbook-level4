package seedu.address.model.apparel;

/**
 * Represents an Apparel's color in the apparel bank.
 */
public class Color {

    private ColorValue primary;

    /**
     * Field must not be null.
     */
    public Color(ColorValue primary) {
        this.primary = primary;
    }

    @Override
    public String toString() {
        return primary.name();
    }
}
