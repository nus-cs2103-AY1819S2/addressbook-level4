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
    NAVY,
    PINK,
    BLACK,
    WHITE,
    GREY,
    KHAKI,
    CREAM;

    /**
     * Return true if a given string is a valid color.
     */
    public static boolean isValidColor(String other) {
        ColorValue[] colors = ColorValue.values();
        for (ColorValue c : colors) {
            if (c.toString().equalsIgnoreCase(other)) {
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
