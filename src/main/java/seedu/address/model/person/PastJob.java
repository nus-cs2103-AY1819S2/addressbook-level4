package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's past job in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPastJob(String)}
 */
public class PastJob {

    public static final String MESSAGE_CONSTRAINTS = "Past jobs can take any values, and it should not be blank";

    /*
     * The first character of the past job must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code PastJob}.
     *
     * @param pastJob A valid past job.
     */
    public PastJob(String pastJob) {
        requireNonNull(pastJob);
        checkArgument(isValidPastJob(pastJob), MESSAGE_CONSTRAINTS);
        value = pastJob;
    }

    /**
     * Returns true if a given string is a valid past job.
     */
    public static boolean isValidPastJob(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PastJob // instanceof handles nulls
                && value.equals(((PastJob) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
