package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class QuizResultDisplay extends UiPart<Region> {

    private static final String FXML = "QuizResultDisplay.fxml";
    private static final String boldStyle = "-fx-font-family: \"Segoe UI\";"
        + "-fx-font-weight: bold;";

    @FXML
    private TextFlow quizResultDisplay;

    public QuizResultDisplay() {
        super(FXML);
    }

    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        quizResultDisplay.getChildren().clear();
        Text feedback = new Text(feedbackToUser);
        quizResultDisplay.getChildren().add(feedback);

        // contains only question
        if (!feedbackToUser.contains("Answer:") && feedbackToUser.contains("Question:")) {
            Text text1 = new Text("Type the ");
            Text answer = new Text("answer ");
            Text text2 = new Text("for the ");
            Text question = new Text("question ");
            Text text3 = new Text("above and press Enter:");

            answer.setStyle(boldStyle);
            question.setStyle(boldStyle);

            quizResultDisplay.getChildren().addAll(text1, answer, text2, question, text3);
        }




    }

}
