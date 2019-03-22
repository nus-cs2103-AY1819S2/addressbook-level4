package seedu.address.model.activity;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Activity's Description
 * Guarantees: immutable; is valid as declared in {@link #isValidDescription(String)}
 */
public class ActivityDescription {

    public static final String MESSAGE_CONSTRAINTS = "Description can take any values, should describe the activity, "
            + "and it should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public static final String DEFAULT_VALUE = "More details to be added.";

    public final String value;

    /**
     * Constructs an {@code ActivityDescription}.
     *
     * @param description A valid description.
     */
    public ActivityDescription(String description) {
        requireNonNull(description);
        checkArgument(isValidDescription(description), MESSAGE_CONSTRAINTS);
        value = description;
    }

    /**
     * Constructs an {@code ActivityDescription}.
     *
     */
    public ActivityDescription() {
        value = DEFAULT_VALUE;
    }


    /**
     * Returns true if a given string is a valid email.
     */
    public static boolean isValidDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ActivityDescription // instanceof handles nulls
                && value.equals(((ActivityDescription) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
