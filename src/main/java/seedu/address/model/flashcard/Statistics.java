package seedu.address.model.flashcard;

import java.util.Objects;
import java.util.Scanner;

/**
 * Represents a Flashcard's statistics on how well the user does in the quiz mode.
 */
public class Statistics {
    public static final String VALIDATION_REGEX = "\\d+ out of \\d+ attempts.$";
    public static final String MESSAGE_CONSTRAINTS = "Statistics string format must be in the form of: "
            + "`<number of success attempt> out of <total number of attempts> attempts.`";

    private final int attemptNumber;
    private final int successAttempt;

    public Statistics(int successAttempt, int attemptNumber) {
        if (successAttempt > attemptNumber) {
            throw new IllegalArgumentException("successAttempt higher than attemptNumber");
        }
        this.attemptNumber = attemptNumber;
        this.successAttempt = successAttempt;
    }

    public Statistics() {
        this.attemptNumber = 0;
        this.successAttempt = 0;
    }

    public Statistics(String fromString) {
        Scanner sc = new Scanner(fromString);
        successAttempt = sc.nextInt();
        sc.next(); // string: out
        sc.next(); // string: of
        attemptNumber = sc.nextInt();
    }

    /**
     * Returns if a given string is a valid statistics format.
     */
    public static boolean isValidStatistics(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        Statistics dummy = new Statistics(test);
        return dummy.successAttempt <= dummy.attemptNumber;
    }

    /**
     * @return The success rate of a quiz on a particular card. Return 0 if there hasn't any attempt in quiz.
     */
    public double getSuccessRate() {
        if (attemptNumber == 0) {
            return 0;
        }
        return (double) successAttempt / attemptNumber;
    }

    /**
     * Returns an updated statistics after a quiz is finished.
     *
     * @param isSuccess does the user guess the card from the quiz correctly.
     * @return the modified statistics
     */
    public Statistics quizAttempt(boolean isSuccess) {
        int rSuccessAttempt = this.successAttempt;
        int rAttemptNumber = this.attemptNumber + 1;
        if (isSuccess) {
            rSuccessAttempt++;
        }
        return new Statistics(rSuccessAttempt, rAttemptNumber);
    }

    /**
     * merge two statistics by combining the attempt and success number.
     *
     * @param oth the statistics to merge
     * @return new Statistics.
     */
    public Statistics merge(Statistics oth) {
        return new Statistics(
            this.successAttempt + oth.successAttempt, this.attemptNumber + oth.attemptNumber);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Statistics // instanceof handles nulls
                && attemptNumber == ((Statistics) other).attemptNumber
                && successAttempt == ((Statistics) other).successAttempt); // state check
    }

    @Override
    public String toString() {
        return String.format("%d out of %d attempts.", successAttempt, attemptNumber);
    }
}
