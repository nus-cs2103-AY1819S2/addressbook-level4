package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Activity's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidActivityName(String)}
 */
public class ActivityName {
    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullActivityName;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public ActivityName(String name) {
        requireNonNull(name);
        checkArgument(isValidActivityName(name), MESSAGE_CONSTRAINTS);
        fullActivityName = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidActivityName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullActivityName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityName // instanceof handles nulls
                && fullActivityName.equals(((ActivityName) other).fullActivityName)); // state check
    }

    @Override
    public int hashCode() {
        return fullActivityName.hashCode();
    }
}
