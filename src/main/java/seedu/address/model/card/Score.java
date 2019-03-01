package seedu.address.model.card;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents a Card's score in the card folder.
 * Guarantees: immutable; is valid as declared in {@link #isValidScore(String)}
 */
public class Score {

    public static final String MESSAGE_CONSTRAINTS = "Score can take a float between 0 and 1 or a string a/b," +
            "where 0 <= a <= b and a and b are integers.";

    public final int correctAttempts;
    public final int totalAttempts;

    /**
     * Constructs an {@code Score}.
     *
     * @param score A valid score string.
     */
    public Score(String score) {
        requireNonNull(score);
        checkArgument(isValidScore(score), MESSAGE_CONSTRAINTS);

        String[] splitString = score.split("/");
        this.correctAttempts = Integer.parseInt(splitString[0]);
        this.totalAttempts = Integer.parseInt(splitString[1]);
    }

    /**
     * Constructs an {@code Score}.
     *
     * @param correctAttempts A valid number of correct attempts.
     * @param totalAttempts A valid number of total attempts.
     */
    public Score(int correctAttempts, int totalAttempts) {
        requireNonNull(correctAttempts);
        requireNonNull(totalAttempts);
        checkArgument(isValidScore(correctAttempts, totalAttempts), MESSAGE_CONSTRAINTS);

        this.correctAttempts = correctAttempts;
        this.totalAttempts = totalAttempts;
    }

    /**
     * Returns true if a given string is a valid score.
     */
    public static boolean isValidScore(String score) {
        String[] splitString = score.split("/");

        if (splitString.length != 2) {
            return false;
        }

        try {
            return isValidScore(Integer.parseInt(splitString[0]), Integer.parseInt(splitString[1]));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns true if given integers form valid score.
     */
    public static boolean isValidScore(int correctAttempts, int totalAttempts) {
        return 0 <= correctAttempts && correctAttempts <= totalAttempts;
    }

    @Override
    public String toString() {
        return String.format("%d/%d", correctAttempts, totalAttempts);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Score // instanceof handles nulls
                && correctAttempts == (((Score) other).correctAttempts)
                && totalAttempts == (((Score) other).totalAttempts));
    }

    @Override
    public int hashCode() {
        return Objects.hash(correctAttempts, totalAttempts);
    }

}
