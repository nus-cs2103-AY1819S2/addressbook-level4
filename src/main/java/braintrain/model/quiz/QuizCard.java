package braintrain.model.quiz;

import static braintrain.commons.util.AppUtil.checkArgument;
import static braintrain.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

/**
 * Represents a partial of Card, only contains the necessary information for Quiz.
 */
public class QuizCard {

    public static final String MESSAGE_CONSTRAINTS = "Question/answer can take any values, and it"
        + " should not be blank or contain only whitespaces";

    private String question;
    private String answer;
    private List<String> opt;
    private int index;
    private boolean swap; //swap qn/ans
    private int totalAttempts;
    private int streak;

    public QuizCard(String question, String answer) {
        requireAllNonNull(question, answer);
        checkArgument(!question.trim().isEmpty() && !answer.isEmpty(), MESSAGE_CONSTRAINTS);

        this.question = question;
        this.answer = answer;
        this.index = -1;
        this.totalAttempts = 0;
        this.streak = 0;
    }

    public QuizCard(String question, String answer, List<String> opt) {
        requireAllNonNull(question, answer, opt);
        checkArgument(!question.trim().isEmpty() && !answer.isEmpty(), MESSAGE_CONSTRAINTS);

        this.question = question;
        this.answer = answer;
        this.opt = opt;
        this.index = -1;
        this.totalAttempts = 0;
        this.streak = 0;
    }

    public QuizCard(int index, String question, String answer) {
        requireAllNonNull(index, question, answer);
        checkArgument(!question.trim().isEmpty() && !answer.isEmpty(), MESSAGE_CONSTRAINTS);

        this.index = index;
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public List<String> getOpt() {
        return opt;
    }

    public int getIndex() {
        if (index > 0) {
            return index;
        }

        throw new IllegalArgumentException("This card do not contain index.");
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public int getStreak() {
        return streak;
    }

    /**
     * Check if the given answer is the same as the answer of the card.
     * @param answer user's input answer.
     * @return the result after checking.
     */
    public boolean isCorrect(String answer) throws NullPointerException {
        return answer.equals(this.answer);
    }

    /**
     * Update both totalAttempts and streak depending on isCorrect
     * @param isCorrect the output of isCorrect method
     */
    public void updateTotalAttemptsandStreak(boolean isCorrect) {
        if (isCorrect) {
            streak += 1;
        } else {
            streak = 0;
        }

        totalAttempts += 1;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof QuizCard)) {
            return false;
        }

        // state check
        QuizCard other = (QuizCard) obj;
        return other.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer);
    }

    //
    //  public QuizCard(Card card, int totalAttempts, int streak) {
    //    this.card = card;
    //    this.totalAttempts = totalAttempts;
    //    this.streak = streak;
    //  }
    //
    //  public void setStreakTotal(boolean isCorrect) {
    //    this.totalAttempts += 1;
    //
    //    if (isCorrect) {
    //      this.streak += 1;
    //    } else {
    //      this.streak = 0;
    //    }
    //  }
}
