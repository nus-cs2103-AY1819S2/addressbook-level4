package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's matricNumber in the club manager.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatricNumber(String)}
 */
public class MatricNumber {

    public static final String MESSAGE_CONSTRAINTS =
            "MatricNumber should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}] ]*";

    public final String matricNumber;

    /**
     * Constructs a {@code Name}.
     *
     * @param matricNumber A valid matricNumber.
     */
    public MatricNumber(String matricNumber) {
        requireNonNull(matricNumber);
        checkArgument(isValidMatricNumber(matricNumber), MESSAGE_CONSTRAINTS);
        this.matricNumber = matricNumber;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidMatricNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return matricNumber;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MatricNumber // instanceof handles nulls
                && matricNumber.equals(((MatricNumber) other).matricNumber)); // state check
    }

    @Override
    public int hashCode() {
        return matricNumber.hashCode();
    }

}
