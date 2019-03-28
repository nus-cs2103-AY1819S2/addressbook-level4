package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.StudyView;


/**
 * The Study Panel of the App.
 */
public class StudyPanel extends UiPart<Region> {

    private static final String FXML = "StudyPanel.fxml";
    private static final PseudoClass ANSWER = PseudoClass.getPseudoClass("answer");

    private final Logger logger = LogsCenter.getLogger(ListPanel.class);
    private final String YOUR_ANSWER_LABEL = "YOUR ANSWER\n\n";

    private final int NUMBER_OF_RATINGS = 5;
    private final int SPACE_SPANNED = 100;

    private final String DIFFICULTY_QUESTION = createRatingQuestion(NUMBER_OF_RATINGS, SPACE_SPANNED);

    @FXML
    private HBox studyPane;

    @FXML
    private HBox card;

    @FXML
    private Label question;

    @FXML
    private Label id;

    @FXML
    private Label userAnswerLabel;

    @FXML
    private Label rateDifficulty;


    public StudyPanel(ObservableValue<String> textShown,
                      ObservableValue<StudyView.studyState> studyState,
                      ObservableValue<String> userAnswer) {
        super(FXML);

        question.setText(textShown.getValue());
        userAnswerLabel.setVisible(false);
        rateDifficulty.setVisible(false);
        card.pseudoClassStateChanged(ANSWER, false);
        userAnswerLabel.setWrapText(true);
        userAnswerLabel.setMaxWidth(500);



        textShown.addListener((observable, oldValue, newValue) -> {
            logger.info("textShown changed to: " + newValue);
            question.setText(textShown.getValue());
        });

        studyState.addListener((observable, oldValue, newValue) -> {
            logger.info("color changed for: " + newValue);
            card.pseudoClassStateChanged(ANSWER, studyState.getValue() == StudyView.studyState.ANSWER);
            question.pseudoClassStateChanged(ANSWER, studyState.getValue() == StudyView.studyState.ANSWER);
            userAnswerLabel.setVisible(studyState.getValue() == StudyView.studyState.ANSWER);
            rateDifficulty.setVisible(studyState.getValue() == StudyView.studyState.ANSWER);
            rateDifficulty.setText(DIFFICULTY_QUESTION);
        });

        userAnswer.addListener((observable, oldValue, newValue) -> {
            logger.info("user answer changed to: " + newValue);
            userAnswerLabel.setText(YOUR_ANSWER_LABEL + userAnswer.getValue());
        });
    }

    private String createRatingQuestion(int noOfRatings, int spaceSpanned) {
        return repeatChar(52, "-") + "\n"
                + "How difficult was that?\n\n"
                + createRatingString(noOfRatings, repeatChar( spaceSpanned/ noOfRatings," "))
                + "\n" + "Easy-peasy" + repeatChar(52, " ") + "Very tough";
    }

    private String createRatingString(int rating, String spaces) {
        return rating == 1 ? "1"
                : (createRatingString(rating - 1, spaces) + spaces + rating);
    }

    private String repeatChar(int num, String repeated) {
        return num == 0 ? "" : (repeated + repeatChar(num - 1, repeated));
    }

}
