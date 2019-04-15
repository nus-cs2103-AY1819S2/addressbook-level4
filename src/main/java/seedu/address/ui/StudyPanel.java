package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.StudyView;


/**
 * The Study Panel of the App.
 */
public class StudyPanel extends MainPanel {

    private static final String FXML = "StudyPanel.fxml";
    private static final PseudoClass ANSWER = PseudoClass.getPseudoClass("answer");
    private static final String YOUR_ANSWER_LABEL = "Your answer was: \n";
    private static final String DIFFICULTY_QUESTION = "How difficult was the question?";

    private final Logger logger = LogsCenter.getLogger(ListPanel.class);


    @FXML
    private HBox studyPane;

    @FXML
    private HBox sCard;

    @FXML
    private Label sQuestion;

    @FXML
    private Label id;

    @FXML
    private Label userAnswerLabel;

    @FXML
    private Label rateDifficulty;

    @FXML
    private VBox status;


    public StudyPanel(ObservableValue<String> textShown, ObservableValue<StudyView.StudyState> studyState,
                      ObservableValue<String> userAnswer) {
        super(FXML);

        sQuestion.setText(textShown.getValue());
        status.setVisible(false);
        rateDifficulty.setText(DIFFICULTY_QUESTION);

        textShown.addListener((observable, oldValue, newValue) -> {
            logger.info("textShown changed to: " + newValue);
            sQuestion.setText(textShown.getValue());
        });

        studyState.addListener((observable, oldValue, newValue) -> {
            logger.info("color changed for: " + newValue);
            sCard.pseudoClassStateChanged(ANSWER, studyState.getValue() == StudyView.StudyState.ANSWER);
            sQuestion.pseudoClassStateChanged(ANSWER, studyState.getValue() == StudyView.StudyState.ANSWER);
            status.setVisible(studyState.getValue() == StudyView.StudyState.ANSWER);
        });

        userAnswer.addListener((observable, oldValue, newValue) -> {
            logger.info("user answer changed to: " + newValue);
            userAnswerLabel.setText(YOUR_ANSWER_LABEL + userAnswer.getValue());
        });
    }
}
