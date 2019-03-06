package seedu.address.quiz;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.quiz.exceptions.NotInitialisedException;
import seedu.address.commons.util.AppUtil;
import seedu.address.commons.util.CollectionUtil;

/**
 * Represents a partial of Card, only contains the necessary information for Quiz.
 */
public class QuizCard {

    public static final String MESSAGE_CONSTRAINTS = "Question/answer can take any values, and it"
        + " should not be blank or contain only whitespaces";
    private Quiz.Mode quizMode;
    private String question;
    private String answer;
    private List<String> opt;
    private int index;
    private int totalAttempts;
    private int streak;

    public QuizCard(String question, String answer) {
        CollectionUtil.requireAllNonNull(question, answer);
        AppUtil.checkArgument(!question.trim().isEmpty() && !answer.isEmpty(), MESSAGE_CONSTRAINTS);

        this.question = question;
        this.answer = answer;
        this.index = -1;
        this.totalAttempts = 0;
        this.streak = 0;
    }

    public QuizCard(String question, String answer, List<String> opt) {
        CollectionUtil.requireAllNonNull(question, answer, opt);
        AppUtil.checkArgument(!question.trim().isEmpty() && !answer.isEmpty(), MESSAGE_CONSTRAINTS);

        this.question = question;
        this.answer = answer;
        this.opt = opt;
        this.index = -1;
        this.totalAttempts = 0;
        this.streak = 0;
    }

    public QuizCard(int index, String question, String answer, Quiz.Mode quizMode) {
        CollectionUtil.requireAllNonNull(index, question, answer, quizMode);
        AppUtil.checkArgument(!question.trim().isEmpty() && !answer.isEmpty(), MESSAGE_CONSTRAINTS);

        this.index = index;
        this.question = question;
        this.answer = answer;
        this.quizMode = quizMode;
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

    public int getIndex() throws NotInitialisedException {
        if (index > -1) {
            return index;
        }

        throw new NotInitialisedException("This card do not contain index.");
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public int getStreak() {
        return streak;
    }

    public Quiz.Mode getQuizMode() {
        requireAllNonNull(quizMode);
        return quizMode;
    }

    /**
     * Check if the given answer is the same as the answer of the card.
     * @param answer user's input answer.
     * @return the result after checking.
     */
    public boolean isCorrect(String answer) throws NullPointerException {
        return answer.equalsIgnoreCase(this.answer);
    }

    /**
     * Update both totalAttempts and streak depending on isCorrect
     * @param isCorrect the output of isCorrect method
     */
    public void updateTotalAttemptsAndStreak(boolean isCorrect) {
        if (quizMode != Quiz.Mode.PREVIEW) {
            if (isCorrect) {
                streak += 1;
            } else {
                streak = 0;
            }
            totalAttempts += 1;
        }
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
        return Objects.hash(index, question, answer, opt, totalAttempts, streak);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Question: " + question + "\n");
        sb.append("Answer: " + answer + "\n");
        sb.append("Optionals: " + opt + "\n");
        sb.append("Index: " + index + "\n");
        sb.append("Total attempts: " + totalAttempts + "\n");
        sb.append("Streak: " + streak + "\n");
        return sb.toString();
    }
}
