package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.ListItem;
import seedu.address.logic.StudyView;
import seedu.address.model.deck.Card;


/**
 * The Study Panel of the App.
 */
public class StudyPanel extends UiPart<Region> {

    private static final String FXML = "StudyPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    private static final PseudoClass ANSWER = PseudoClass.getPseudoClass("answer");

    @FXML
    private HBox studyPane;

    @FXML
    private HBox card;

    @FXML
    private Label question;

    @FXML
    private Label id;


    public StudyPanel(ObservableValue<String> textShown, ObservableValue<StudyView.studyState> studyState) {
        super(FXML);

        question.setText(textShown.getValue());
        card.pseudoClassStateChanged(ANSWER, false);

        textShown.addListener((observable, oldValue, newValue) -> {
            logger.info("textShown changed to: " + newValue);
            question.setText(textShown.getValue());

        });

        textShown.addListener((observable, oldValue, newValue) -> {
            logger.info("color changed to: " + newValue);
            card.pseudoClassStateChanged(ANSWER, studyState.getValue() == StudyView.studyState.ANSWER);
        });



    }

}
