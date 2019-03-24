package seedu.address.model.modelmanager.quiz;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

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
    private boolean hasAttemptedBefore;
    private boolean isWrongTwice;
    private boolean isCardDifficult;

    public QuizCard(String question, String answer) {
        requireAllNonNull(question, answer);
        checkArgument(!question.trim().isEmpty() && !answer.isEmpty(), MESSAGE_CONSTRAINTS);

        this.question = question;
        this.answer = answer;
        this.index = -1;
        this.totalAttempts = 0;
        this.streak = 0;
        this.isCardDifficult = false;
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
        this.isCardDifficult = false;
    }

    public QuizCard(int index, String question, String answer, Quiz.Mode quizMode) {
        requireAllNonNull(index, question, answer, quizMode);
        checkArgument(!question.trim().isEmpty() && !answer.isEmpty(), MESSAGE_CONSTRAINTS);

        this.index = index;
        this.question = question;
        this.answer = answer;
        this.quizMode = quizMode;
        this.hasAttemptedBefore = false;
        this.isWrongTwice = false;
        this.isCardDifficult = false;
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
        assert index > -1;

        return index;
    }

    public int getTotalAttempts() {
        return totalAttempts;
    }

    public int getStreak() {
        return streak;
    }

    public boolean isWrongTwice() {
        return isWrongTwice;
    }

    public Quiz.Mode getQuizMode() {
        requireAllNonNull(quizMode);
        return quizMode;
    }

    /**
     * Returns if the card labeled difficult.
     */
    public boolean isCardDifficult() {
        assert index == -1;

        return isCardDifficult;
    }

    /**
     * Toggles between if the card labeled difficult.
     */
    public void toggleIsCardDifficult() {
        assert index == -1;

        isCardDifficult = !isCardDifficult;
    }

    /**
     * Check if the given answer is the same as the answer of the card.
     * @param answer user's input answer.
     * @return the result after checking.
     */
    public boolean isCorrect(String answer) throws NullPointerException {
        if (hasAttemptedBefore) {
            isWrongTwice = true;
        }
        hasAttemptedBefore = true;
        return answer.equalsIgnoreCase(this.answer);
    }

    /**
     * Update both totalAttempts and streak depending on isCorrect.
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
