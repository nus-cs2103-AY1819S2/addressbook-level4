package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Activity's Date and Time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidActivityDateTime(String)}
 */
public class ActivityDateTime {
    // to be updated

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullDateTime;

    /**
     * Constructs a {@code ActivityDateTime}.
     *
     * @param dateTime A valid datetime.
     */
    public ActivityDateTime(String dateTime) {
        requireNonNull(dateTime);
        checkArgument(isValidActivityDateTime(dateTime), MESSAGE_CONSTRAINTS);
        fullDateTime = dateTime;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidActivityDateTime(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return fullDateTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityDateTime // instanceof handles nulls
                && fullDateTime.equals(((ActivityDateTime) other).fullDateTime)); // state check
    }

    @Override
    public int hashCode() {
        return fullDateTime.hashCode();
    }
}
