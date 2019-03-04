package seedu.address.model.apparel;

/**
 * Represents the primary type of an apparel.
 */
public enum TypePrimaryValue {
    TOP,
    BOTTOM,
    BELT,
    SHOES;

    /**
     * Return true if a given string is a valid type.
     */
    public static boolean isValidType(String other) {
        TypePrimaryValue[] types = TypePrimaryValue.values();
        for (TypePrimaryValue t : types) {
            if (TypePrimaryValue.valueOf(other.toUpperCase()) == t) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return true if a given string is a valid type.
     */
    public static boolean isValidType(TypePrimaryValue other) {
        TypePrimaryValue[] types = TypePrimaryValue.values();
        for (TypePrimaryValue t : types) {
            if (other == t) {
                return true;
            }
        }

        return false;
    }
}
