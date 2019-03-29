package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Condition in the request book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidConditionName(String)}
 */
public class Condition {

    public static final String MESSAGE_CONSTRAINTS = "Condition descriptions should be alphanumeric characters/spaces";

    /**
     * The first character of the condition must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_CONDITION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    private final String conditionName;

    /**
     * Constructs a {@code Condition}.
     *
     * @param conditionName A valid Condition name.
     */
    public Condition(String conditionName) {
        requireNonNull(conditionName);
        checkArgument(isValidConditionName(conditionName), MESSAGE_CONSTRAINTS);
        this.conditionName = conditionName;
    }

    /**
     * Returns true if a given string is a valid Condition name.
     */
    public static boolean isValidConditionName(String test) {
        return test.matches(VALIDATION_CONDITION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Condition // instanceof handles nulls
                && conditionName.equals(((Condition) other).conditionName)); // state check
    }

    @Override
    public int hashCode() {
        return conditionName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return conditionName;
    }

}
