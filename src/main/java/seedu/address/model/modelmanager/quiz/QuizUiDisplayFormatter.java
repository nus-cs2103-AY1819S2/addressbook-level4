package seedu.address.model.modelmanager.quiz;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
}
