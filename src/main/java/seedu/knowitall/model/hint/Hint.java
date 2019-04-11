package seedu.knowitall.model.hint;

import static java.util.Objects.requireNonNull;
import static seedu.knowitall.commons.util.AppUtil.checkArgument;

/**
 * Represents a Hint in the card folder.
 * Guarantees: immutable; is valid as declared in {@link #isValidHintName(String)}
 */
public class Hint {

    public static final String MESSAGE_CONSTRAINTS = "Hints can take any values, and should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    private static final int MAX_LENGTH = 512;

    public final String hintName;

    /**
     * Constructs a {@code Hint}.
     *
     * @param hintName A valid hint name.
     */
    public Hint(String hintName) {
        requireNonNull(hintName);
        checkArgument(isValidHintName(hintName), MESSAGE_CONSTRAINTS);
        this.hintName = hintName;
    }

    /**
     * Returns true if a given string is a valid hint name.
     */
    public static boolean isValidHintName(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= MAX_LENGTH;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Hint // instanceof handles nulls
                && hintName.equals(((Hint) other).hintName)); // state check
    }

    @Override
    public int hashCode() {
        return hintName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + hintName + ']';
    }

}
