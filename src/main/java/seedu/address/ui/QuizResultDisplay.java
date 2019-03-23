package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.model.modelmanager.quiz.Quiz;
import seedu.address.model.modelmanager.quiz.QuizUiDisplayFormatter;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class QuizResultDisplay extends UiPart<Region> {
    public static final String MESSAGE_QUESTION = "%1$s: %2$s\n";
    public static final String MESSAGE_QUESTION_ANSWER = "%1$s: %2$s\n%3$s: %4$s\n";

    private static final String FXML = "QuizResultDisplay.fxml";
    private static final String boldStyle = "-fx-font-family: \"Segoe UI\";"
        + "-fx-font-weight: bold;";

    @FXML
    private TextFlow quizResultDisplay;

    public QuizResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(QuizUiDisplayFormatter formatter) {
        quizResultDisplay.getChildren().clear();

        if (formatter != null) {
            // contains only question
            Quiz.Mode mode = formatter.getMode();
            Text questionAnswer = new Text();

            switch (mode) {
            case PREVIEW:
                questionAnswer.setText(String.format(MESSAGE_QUESTION_ANSWER, formatter.getQuestionHeader(),
                    formatter.getQuestion(), formatter.getAnswerHeader(), formatter.getAnswer()));
                Text text = new Text("Press Enter to go to the next question");
                quizResultDisplay.getChildren().addAll(questionAnswer, text);
                break;
            case REVIEW:
                questionAnswer.setText(String.format(MESSAGE_QUESTION, formatter.getQuestionHeader(),
                    formatter.getQuestion()));
                Text text1 = new Text("Type the ");
                Text answer = new Text(formatter.getAnswerHeader() + " ");
                Text text2 = new Text("for the ");
                Text question = new Text(formatter.getQuestionHeader() + " ");
                Text text3 = new Text("above and press Enter");

                answer.setStyle(boldStyle);

                quizResultDisplay.getChildren().addAll(questionAnswer, text1, answer, text2, question, text3);
                break;
            default:
                break;
            }
        } else {
            quizResultDisplay.getChildren().add(new Text(""));

        }





    }

}
