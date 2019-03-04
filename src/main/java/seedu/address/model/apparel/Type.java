package seedu.address.model.apparel;

/**
 * Represents an apparel's type in the apparel bank.
 */
public class Type {

    private final TypePrimaryValue primary;

    // private final TypePrimaryValue secondary;

    /**
     * Field must not be null.
     */
    public Type(TypePrimaryValue primary /* , TypeSecondaryValue secondary*/) {
        this.primary = primary;
        //this.secondary = secondary;
    }

    @Override
    public String toString() {
        return primary.name();
    }
}
