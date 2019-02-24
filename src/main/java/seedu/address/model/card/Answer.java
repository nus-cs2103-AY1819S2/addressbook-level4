package seedu.address.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Card's answer number in the card folder.
 * Guarantees: immutable; is valid as declared in {@link #isValidAnswer(String)}
 */
public class Answer {


    public static final String MESSAGE_CONSTRAINTS =
            "Answer numbers should only contain numbers, and it should be at least 3 digits long";
    public static final String VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code Answer}.
     *
     * @param answer A valid answer number.
     */
    public Answer(String answer) {
        requireNonNull(answer);
        checkArgument(isValidAnswer(answer), MESSAGE_CONSTRAINTS);
        value = answer;
    }

    /**
     * Returns true if a given string is a valid answer number.
     */
    public static boolean isValidAnswer(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Answer // instanceof handles nulls
                && value.equals(((Answer) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
