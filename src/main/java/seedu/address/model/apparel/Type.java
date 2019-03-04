package seedu.address.model.apparel;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an apparel's type in the apparel bank.
 */
public class Type {

    public static final String MESSAGE_CONSTRAINTS =
            "Types that are accepted are TOP, BOTTOM, BELT, SHOES.";

    private final TypePrimaryValue primary;

    // private final TypePrimaryValue secondary;

    /**
     * Constructs a {@code Type}.
     *
     * @param primary A valid primary type.
     */
    public Type(TypePrimaryValue primary) {
        requireNonNull(primary);
        checkArgument(TypePrimaryValue.isValidType(primary), MESSAGE_CONSTRAINTS);

        this.primary = primary;
    }

    /**
     * Constructs a {@code Type}.
     *
     * @param primary A valid primary type.
     */
    public Type(String primary) {
        requireNonNull(primary);
        checkArgument(TypePrimaryValue.isValidType(primary), MESSAGE_CONSTRAINTS);

        this.primary = TypePrimaryValue.valueOf(primary.toUpperCase());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Type // instanceof handles nulls
                && primary == (((Type) other).primary)); // state check
    }

    @Override
    public String toString() {
        return primary.name();
    }
}
