package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
//import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a ConditionTag --> accepts any form of alphanumeric input from Patient
 * Guarantees: immutable; name is valid as declared in {@link #isValidConditionTagName(String)}
 */
public class ConditionTag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphabetic";
    public static final String VALIDATION_REGEX = "(?<=\\s|^)[a-zA-Z]*(?=[.,;:]?\\s|$)";

    public final String conditionTagName;

    /**
     * Constructs a {@code ConditionTag}.
     *
     * @param conditionTagName A valid ConditionTag name.
     */
    public ConditionTag(String conditionTagName) {
        requireNonNull(conditionTagName);
        //checkArgument(isValidConditionTagName(conditionTagName), MESSAGE_CONSTRAINTS);
        this.conditionTagName = conditionTagName;
    }

    /**
     * Returns true if a given string is a valid ConditionTag name.
     */
    public static boolean isValidConditionTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static ConditionTag parseString(String input) {
        return new ConditionTag(input);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ConditionTag // instanceof handles nulls
            && conditionTagName.equals(((ConditionTag) other).conditionTagName)); // state check
    }

    @Override
    public int hashCode() {
        return conditionTagName.hashCode();
    }

    /**
     * Returns name (string) of Condition Tag
     */
    public String getName() {
        return conditionTagName;
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return '[' + conditionTagName + ']';
    }

}
