package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Jobs Applying For in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidJobsApply(String)}
 */
public class JobsApply {

    public static final String MESSAGE_CONSTRAINTS = "Job(s) Applying For should only contain alphanumeric"
            + " characters, and it should not be blank";
    /*
     * The first character of the job must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\w][\\w ]*";

    public final String value;

    /**
     * Constructs an {@code JobsApply}.
     *
     * @param jobsApply A valid job applying for.
     */
    public JobsApply(String jobsApply) {
        requireNonNull(jobsApply);
        checkArgument(isValidJobsApply(jobsApply), MESSAGE_CONSTRAINTS);
        value = jobsApply;
    }

    /**
     * Returns true if a given string is a valid job applying for.
     */
    public static boolean isValidJobsApply(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof JobsApply // instanceof handles nulls
                && value.equals(((JobsApply) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
