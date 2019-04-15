package seedu.address.model.quiz;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

/**
 * Represents the QuizCard used in {@link Quiz}.
 */
public class QuizCard {

    public static final String MESSAGE_CONSTRAINTS = "Question/answer can take any values, and it"
        + " should not be blank or contain only whitespaces";
    private QuizMode quizMode;
    private String question;
    private String answer;
    private String questionHeader;
    private String answerHeader;
    private List<String> opt;
    private int index;
    private int totalAttempts;
    private int streak;
    private boolean hasAttemptedBefore;
    private boolean isWrongTwice;
    private boolean isCardDifficult;

    public QuizCard(String question, String answer, List<String> opt, String questionHeader, String answerHeader) {
        requireAllNonNull(question, answer, opt, questionHeader, answerHeader);
        checkArgument(!question.trim().isEmpty() && !answer.trim().isEmpty(), MESSAGE_CONSTRAINTS);

        this.question = question;
        this.answer = answer;
        this.questionHeader = questionHeader;
        this.answerHeader = answerHeader;
        this.opt = opt;
        this.index = -1;
        this.totalAttempts = 0;
        this.streak = 0;
        this.isCardDifficult = false;
    }

    private QuizCard(int index, String question, String answer, List<String> opt, String questionHeader,
                     String answerHeader, QuizMode quizMode) {
        requireAllNonNull(index, question, answer, quizMode, questionHeader, answerHeader);
        checkArgument(!question.trim().isEmpty() && !answer.trim().isEmpty(), MESSAGE_CONSTRAINTS);

        this.index = index;
        this.question = question;
        this.answer = answer;
        this.questionHeader = questionHeader;
        this.answerHeader = answerHeader;
        this.opt = opt;
        this.quizMode = quizMode;

        this.totalAttempts = -1;
        this.streak = -1;
        this.isWrongTwice = false;
        this.hasAttemptedBefore = false;
        this.isCardDifficult = false;
    }

    public QuizCard generateOrderedQuizCardWithIndex(int index, QuizMode mode) {
        return new QuizCard(index, question, answer, opt, questionHeader, answerHeader, mode);
    }

    public QuizCard generateFlippedQuizCardWithIndex(int index) {
        return new QuizCard(index, answer, question, opt, answerHeader, questionHeader, QuizMode.REVIEW);
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

    public QuizMode getQuizMode() {
        return quizMode;
    }

    public String getQuestionHeader() {
        return questionHeader;
    }

    public String getAnswerHeader() {
        return answerHeader;
    }

    public boolean isCardDifficult() {
        return isCardDifficult;
    }

    /**
     * Toggles between if the card labeled difficult.
     */
    public void toggleIsCardDifficult() {
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
            quizMode = QuizMode.PREVIEW;
        }
        hasAttemptedBefore = true;
        return answer.equalsIgnoreCase(this.answer);
    }

    /**
     * Update both totalAttempts and streak depending on isCorrect.
     * @param isCorrect the output of isCorrect method
     */
    public void updateTotalAttemptsAndStreak(boolean isCorrect) {
        if (quizMode != QuizMode.PREVIEW) {
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
        return Objects.hash(index, question, answer, questionHeader, answerHeader, opt, totalAttempts, streak);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(questionHeader + ": " + question + "\n");
        sb.append(answerHeader + ": " + answer + "\n");
        sb.append("hints: " + opt + "\n");

        if (index == -1) {
            sb.append("Total attempts: " + totalAttempts + "\n");
            sb.append("Streak: " + streak + "\n");
            sb.append("isWrongTwice: " + isWrongTwice + "\n");
        } else {
            sb.append("Index: " + index + "\n");
        }

        return sb.toString();
    }
}
