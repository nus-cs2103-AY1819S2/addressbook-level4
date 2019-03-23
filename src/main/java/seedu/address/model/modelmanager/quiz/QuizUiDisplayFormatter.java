package seedu.address.model.modelmanager.quiz;

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
    private Quiz.Mode mode;

    public QuizUiDisplayFormatter(String questionHeader, String question, String answerHeader, String answer,
                                  Quiz.Mode mode) {
        requireAllNonNull(questionHeader, question, answerHeader, answer);
        this.questionHeader = questionHeader;
        this.question = question;
        this.answerHeader = answerHeader;
        this.answer = answer;
        this.mode = mode;
    }

    public QuizUiDisplayFormatter(String questionHeader, String question, String answerHeader, Quiz.Mode mode) {
        requireAllNonNull(questionHeader, question, answerHeader);
        this.questionHeader = questionHeader;
        this.question = question;
        this.answerHeader = answerHeader;
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

    public Quiz.Mode getMode() {
        return mode;
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
