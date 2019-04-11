package seedu.knowitall.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.knowitall.commons.util.AppUtil.checkArgument;

/**
 * Represents a Card's answer in the card folder.
 * Guarantees: immutable; is valid as declared in {@link #isValidAnswer(String)}
 */
public class Answer {

    public static final String MESSAGE_CONSTRAINTS = "Answers can take any values, and should not be blank";
    public static final String VALIDATION_REGEX = "[^\\s].*";
    private static final int MAX_LENGTH = 512;

    public final String fullAnswer;

    /**
     * Constructs a {@code Answer}.
     *
     * @param answer A valid answer number.
     */
    public Answer(String answer) {
        requireNonNull(answer);
        checkArgument(isValidAnswer(answer), MESSAGE_CONSTRAINTS);
        fullAnswer = answer;
    }

    /**
     * Returns true if a given string is a valid answer number.
     */
    public static boolean isValidAnswer(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= MAX_LENGTH;
    }

    @Override
    public String toString() {
        return fullAnswer;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Answer // instanceof handles nulls
                && fullAnswer.equals(((Answer) other).fullAnswer)); // state check
    }

    @Override
    public int hashCode() {
        return fullAnswer.hashCode();
    }

}
