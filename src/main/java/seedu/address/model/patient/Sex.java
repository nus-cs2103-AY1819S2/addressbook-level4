package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Sex in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidSex(String)}
 */
public class Sex {

    public static final String MESSAGE_CONSTRAINTS =
        "Sex should only be either M or F";
    public static final String VALIDATION_REGEX = "^[M,F]$";
    public final String value;

    /**
     * Constructs a {@code Sex}.
     *
     * @param sex A valid Sex value.
     */
    public Sex(String sex) {
        requireNonNull(sex);
        checkArgument(isValidSex(sex), MESSAGE_CONSTRAINTS);
        value = sex;
    }

    /**
     * Returns true if a given string is a valid Sex number.
     */
    public static boolean isValidSex(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getSex() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Sex // instanceof handles nulls
            && value.equals(((Sex) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return "(" + value + ")";
    }

}
