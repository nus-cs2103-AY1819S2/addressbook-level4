package seedu.address.model.apparel;

/**
 * Represents the value of the color of apparel.
 * Accepted color that already has matching relation established.
 * For new color to be added, make sure the new color has a matching relation to any other
 * pre-defined colors.
 */
public enum ColorValue {
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    BLUE,
    PURPLE,
    BROWN,
    MAGENTA,
    TAN,
    CYAN,
    OLIVE,
    MAROON,
    NAVY,
    AQUAMARINE,
    TURQUOISE,
    SILVER,
    LIME,
    TEAL,
    INDIGO,
    VIOLET,
    PINK,
    BLACK,
    WHITE,
    GREY;

    /**
     * Return true if a given string is a valid color.
     */
    public static boolean isValidColor(String other) {
        ColorValue[] colors = ColorValue.values();
        for (ColorValue c : colors) {
            if (ColorValue.valueOf(other.toUpperCase()) == c) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return true if a given color value is a valid color.
     */
    public static boolean isValidColor(ColorValue other) {
        ColorValue[] colors = ColorValue.values();
        for (ColorValue c : colors) {
            if (other == c) {
                return true;
            }
        }

        return false;
    }


}
