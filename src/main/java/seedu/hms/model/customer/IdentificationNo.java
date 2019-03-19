package seedu.hms.model.customer;

import static java.util.Objects.requireNonNull;
import static seedu.hms.commons.util.AppUtil.checkArgument;

/**
 * Represents a Customer's identification number in the hms book.
 * Guarantees: immutable; is valid as declared in {@link #isValidIdNum(String)}
 */
public class IdentificationNo {


    public static final String MESSAGE_CONSTRAINTS =
        "Identification numbers should only contain numbers, and it should be at least 3 digits long";
    public static final String VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code IdentificationNo}.
     *
     * @param idnum A valid identification number.
     */
    public IdentificationNo(String idnum) {
        requireNonNull(idnum);
        checkArgument(isValidIdNum(idnum), MESSAGE_CONSTRAINTS);
        value = idnum;
    }

    /**
     * Returns true if a given string is a valid identification number.
     */
    public static boolean isValidIdNum(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof IdentificationNo // instanceof handles nulls
            && value.equals(((IdentificationNo) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
