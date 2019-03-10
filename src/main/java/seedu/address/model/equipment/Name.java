package seedu.address.model.equipment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Equipment's name in the Equipment Manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidSerialNumber(String)}
 */
public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Serial Number should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String serialNumber;

    /**
     * Constructs a {@code Name}.
     *
     * @param serialNumber A valid serial_number number.
     */
    public Name(String serialNumber) {
        requireNonNull(serialNumber);
        checkArgument(isValidSerialNumber(serialNumber), MESSAGE_CONSTRAINTS);
        this.serialNumber = serialNumber;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidSerialNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return serialNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Name // instanceof handles nulls
                && serialNumber.equals(((Name) other).serialNumber)); // state check
    }

    @Override
    public int hashCode() {
        return serialNumber.hashCode();
    }

}
