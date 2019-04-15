package seedu.equipment.model.equipment;

import static java.util.Objects.requireNonNull;
import static seedu.equipment.commons.util.AppUtil.checkArgument;

/**
 * Represents a Equipment's serial number in the Equipment Manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidSerialNumber(String)}
 */
public class SerialNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "Serial Number should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the serial number must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String serialNumber;

    /**
     * Constructs a {@code Name}.
     *
     * @param serialNum A valid serial number.
     */
    public SerialNumber(String serialNum) {
        requireNonNull(serialNum);
        checkArgument(isValidSerialNumber(serialNum), MESSAGE_CONSTRAINTS);
        this.serialNumber = serialNum;
    }

    /**
     * Returns true if a given string is a valid serial number.
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
                || (other instanceof SerialNumber // instanceof handles nulls
                && serialNumber.equals(((SerialNumber) other).serialNumber)); // state check
    }

    @Override
    public int hashCode() {
        return serialNumber.hashCode();
    }
}
