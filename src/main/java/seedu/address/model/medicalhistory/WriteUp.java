package seedu.address.model.medicalhistory;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents write up of a medical history in the docX.
 * Guarantees: immutable; is valid as declared in {@link #isValidWriteUp(String)}
 */
public class WriteUp {

    public static final String MESSAGE_CONSTRAINTS =
            "Write up should be a short description";
    //public static final String VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code WriteUp}.
     *
     * @param writeUp A valid write up.
     */
    public WriteUp(String writeUp) {
        requireNonNull(writeUp);
        checkArgument(isValidWriteUp(writeUp), MESSAGE_CONSTRAINTS);
        value = writeUp;
    }

    /**
     * Returns true if a given string is a valid write up.
     */
    public static boolean isValidWriteUp(String test) {
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WriteUp // instanceof handles nulls
                && value.equals(((WriteUp) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}



