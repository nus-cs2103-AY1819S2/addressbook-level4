package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's interview scores in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidInterviewScores(String)}
 */
public class InterviewScores {


    public static final String MESSAGE_CONSTRAINTS =
            "Interview Scores should only exactly 5 set of numbers, each number separated by a comma";
    public static final String VALIDATION_REGEX = "\\d+" + "," + "\\d+" + "," + "\\d+" + "," + "\\d+"
            + "," + "\\d+";
    public static final String NO_RECORD = "No Record";
    public final String value;

    /**
     * Constructs a {@code InterviewScores}.
     *
     * @param interviewScores A valid set of interview scores.
     */
    public InterviewScores(String interviewScores) {
        requireNonNull(interviewScores);
        checkArgument(isValidInterviewScores(interviewScores), MESSAGE_CONSTRAINTS);
        value = interviewScores;
    }

    /**
     * Returns true if a given string is a valid set of interview scores.
     */
    public static boolean isValidInterviewScores(String test) {
        return
                test.matches(VALIDATION_REGEX) || test.matches(NO_RECORD);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InterviewScores // instanceof handles nulls
                && value.equals(((InterviewScores) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
