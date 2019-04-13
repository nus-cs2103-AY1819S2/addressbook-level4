package seedu.knowitall.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.knowitall.commons.util.AppUtil.checkArgument;

/**
 * Represents a Card's incorrect Option, if any, in the card folder.
 * Guarantees: immutable; is valid as declared in {@link #isValidOption(String)}
 */
public class Option {

    public static final int MAX_LENGTH = 512;
    public static final String MESSAGE_CONSTRAINTS = "Options can take any values, should not be blank, and should"
            + " be less than " + MAX_LENGTH + " characters";
    public static final String VALIDATION_INDEX = "[^\\s].*";

    public final String optionValue;

    /**
     * Constructs a {@code Option}.
     *
     * @param value A valid option value.
     */
    public Option(String value) {
        requireNonNull(value);
        checkArgument(isValidOption(value), MESSAGE_CONSTRAINTS);
        this.optionValue = value;
    }
    /**
     * Returns true if a given string is a valid option value.
     */
    public static boolean isValidOption(String test) {
        return test.matches(VALIDATION_INDEX) && test.length() <= MAX_LENGTH;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Option // instanceof handles nulls
                && optionValue.equals(((Option) other).optionValue)); // state check
    }

    @Override
    public int hashCode() {
        return optionValue.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + optionValue + ']';
    }

}
