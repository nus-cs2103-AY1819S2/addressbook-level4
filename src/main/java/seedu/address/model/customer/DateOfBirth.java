package seedu.address.model.customer;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Customer's identification number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDob(String)}
 */
public class DateOfBirth {


    public static final String MESSAGE_CONSTRAINTS =
        "Date of Birth should be of the format dd/mm/yyyy ";
    public static final String VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code DateOfBirth}.
     *
     * @param dob A valid identification number.
     */
    public DateOfBirth(String dob) {
        requireNonNull(dob);
        checkArgument(isValidDob(dob), MESSAGE_CONSTRAINTS);
        value = dob;
    }

    /**
     * Returns true if a given string is a valid date of birth.
     */
    public static boolean isValidDob(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DateOfBirth // instanceof handles nulls
            && value.equals(((DateOfBirth) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
