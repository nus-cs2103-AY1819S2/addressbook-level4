package seedu.address.model.quiz;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Quiz commands uses this to format the output to Ui display panel
 */
public class QuizUiDisplayFormatter {
    private String questionHeader;
    private String question;
    private String answerHeader;

    private String answer;
    private int totalCorrect;
    private QuizMode mode;
    private boolean isWrongTwice;
    public QuizUiDisplayFormatter(String questionHeader, String question, String answerHeader, String answer,
                                  int totalCorrect, QuizMode mode) {
        requireAllNonNull(questionHeader, question, answerHeader, answer);
        this.questionHeader = questionHeader;
        this.question = question;
        this.answerHeader = answerHeader;
        this.answer = answer;
        this.totalCorrect = totalCorrect;
        this.mode = mode;
    }
    public QuizUiDisplayFormatter(String questionHeader, String question, String answerHeader, String answer,
                                  int totalCorrect, QuizMode mode, boolean isWrongTwice) {
        requireAllNonNull(questionHeader, question, answerHeader, answer);
        this.questionHeader = questionHeader;
        this.question = question;
        this.answerHeader = answerHeader;
        this.answer = answer;
        this.totalCorrect = totalCorrect;
        this.mode = mode;
        this.isWrongTwice = isWrongTwice;
    }

    public QuizUiDisplayFormatter(String questionHeader, String question, String answerHeader, int totalCorrect,
                                  QuizMode mode) {
        requireAllNonNull(questionHeader, question, answerHeader);
        this.questionHeader = questionHeader;
        this.question = question;
        this.answerHeader = answerHeader;
        this.totalCorrect = totalCorrect;
        this.mode = mode;
    }

    public String getQuestionHeader() {
        return questionHeader;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswerHeader() {
        return answerHeader;
    }

    public String getAnswer() {
        return answer;
    }

    public QuizMode getMode() {
        return mode;
    }
    public int getTotalCorrect() {
        return totalCorrect;
    }

    public boolean isWrongTwice() {
        return isWrongTwice;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof QuizUiDisplayFormatter)) {
            return false;
        }

        // state check
        QuizUiDisplayFormatter other = (QuizUiDisplayFormatter) obj;
        return other.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionHeader, question, answerHeader, answer, mode);
    }
}
