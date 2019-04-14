package seedu.address.ui;

import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.model.quiz.QuizCard;
import seedu.address.model.quiz.QuizMode;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class MainPanel extends UiPart<Region> {
    public static final String MESSAGE_QUESTION = "%1$s: %2$s\n";
    public static final String MESSAGE_QUESTION_ANSWER = "%1$s: %2$s\n%3$s: %4$s\n";

    private static final String FXML = "MainPanel.fxml";
    private static final String boldStyle = "-fx-font-weight: bold;";

    @FXML
    private TextFlow mainPanel;

    private QuizCard quizCard;
    private String totalCorrectAndTotalAttempts;

    public MainPanel() {
        super(FXML);
    }

    /**
     * Displays the current testing card and status information in the main panel.
     * @param quizCard the current testing quizCard.
     * @param totalCorrectAndTotalAttempts the current status stored in totalCorrectAndTotalAttempts.
     */
    public void setFeedbackToUser(QuizCard quizCard, String totalCorrectAndTotalAttempts) {
        mainPanel.getChildren().clear();

        if (quizCard != null) {
            this.quizCard = quizCard;
            this.totalCorrectAndTotalAttempts = totalCorrectAndTotalAttempts;
            QuizMode mode = quizCard.getQuizMode();
            Text questionAnswer = new Text();

            switch (mode) {
            case PREVIEW:
                questionAnswer.setText(String.format(MESSAGE_QUESTION_ANSWER, quizCard.getQuestionHeader(),
                    quizCard.getQuestion(), quizCard.getAnswerHeader(), quizCard.getAnswer()));

                if (!quizCard.isWrongTwice()) {
                    Text text = new Text("\nPress Enter to go to the next question");
                    mainPanel.getChildren().addAll(questionAnswer, text);
                    setTotalCorrect();
                    break;
                }
                mainPanel.getChildren().add(questionAnswer);
                setAnswerPrompt();
                setTotalCorrect();
                break;
            case REVIEW:
                questionAnswer.setText(String.format(MESSAGE_QUESTION, quizCard.getQuestionHeader(),
                    quizCard.getQuestion()));
                mainPanel.getChildren().add(questionAnswer);
                setAnswerPrompt();
                setTotalCorrect();
                break;
            default:
                break;
            }
        } else {
            mainPanel.getChildren().add(new Text(""));

        }
    }

    private void setAnswerPrompt() {
        Text text1 = new Text("\nType the ");
        Text answer = new Text(quizCard.getAnswerHeader() + " ");
        Text text2 = new Text("for the " + quizCard.getQuestionHeader() + " above and press Enter");
        answer.setStyle(boldStyle);
        List<Text> texts = Arrays.asList(text1, answer, text2);
        mainPanel.getChildren().addAll(texts);
    }
    private void setTotalCorrect() {
        Text text1 = new Text("\n\nCurrent total correct questions: " + totalCorrectAndTotalAttempts);
        mainPanel.getChildren().add(text1);
    }
}
