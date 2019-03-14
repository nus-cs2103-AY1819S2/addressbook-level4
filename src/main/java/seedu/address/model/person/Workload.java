package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's workload number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidWorkload(String)}
 */
public class Workload {

    public static final String MESSAGE_CONSTRAINTS =
            "Workload should be a non-negative number to .5 degree of accuracy";
    public static final String VALIDATION_REGEX = "\\d{1,3}(\\.[05])?";
    public final String value;

    /**
     * Constructs a {@code Workload}.
     *
     * @param workload A valid workload number.
     */
    public Workload(String workload) {
        requireNonNull(workload);
        checkArgument(isValidWorkload(workload), MESSAGE_CONSTRAINTS);
        value = workload;
    }

    /**
     * Returns true if a given string is a valid workload number.
     */
    public static boolean isValidWorkload(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Workload // instanceof handles nulls
                && value.equals(((Workload) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
