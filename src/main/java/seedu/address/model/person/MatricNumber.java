package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's matricNumber in the club manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatricNumber(String)}
 */
public class MatricNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "MatricNumber should only contain alphanumeric characters, starting with an A or U"
                    + " and ending with an uppercase alphabet,"
                    + " with 7 digits in between the first and last character.";
    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[AU]\\d{7}[A-Z]";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param matricNumber A valid matricNumber.
     */
    public MatricNumber(String matricNumber) {
        requireNonNull(matricNumber);
        checkArgument(isValidMatricNumber(matricNumber), MESSAGE_CONSTRAINTS);
        this.value = matricNumber;
    }

    /**
     * Returns true if both persons of the same matric number.
     */
    public boolean isSameMatricNumber(MatricNumber otherMatricNumber) {

        return otherMatricNumber != null
                && otherMatricNumber.value.equalsIgnoreCase(this.value);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidMatricNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatricNumber // instanceof handles nulls
                && value.equals(((MatricNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
