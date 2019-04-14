package seedu.address.model.apparel;

/**
 * Represents the primary type of an apparel.
 */
public enum ClothingTypeValue {
    TOP,
    BOTTOM,
    BELT,
    SHOES;

    /**
     * Return true if a given string is a valid type.
     */
    public static boolean isValidClothingType(String other) {
        ClothingTypeValue[] types = ClothingTypeValue.values();
        for (ClothingTypeValue t : types) {
            if (t.toString().equalsIgnoreCase(other)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return true if a given string is a valid type.
     */
    public static boolean isValidClothingType(ClothingTypeValue other) {
        ClothingTypeValue[] types = ClothingTypeValue.values();
        for (ClothingTypeValue t : types) {
            if (other == t) {
                return true;
            }
        }

        return false;
    }
}
