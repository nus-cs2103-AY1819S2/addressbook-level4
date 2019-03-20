package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
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
import seedu.address.model.deck.Card;

/**
 * The Question Panel of the App.
 */
public class QuestionPanel extends UiPart<Region> {

    private static final String FXML = "QuestionPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ListPanel.class);

    @FXML
    private HBox questionPane;

    @FXML
    private Label question;

    @FXML
    private Label id;


    public QuestionPanel(ObservableValue<String> textShown) {
        super(FXML);

        question.setText(textShown.getValue());

        textShown.addListener((observable, oldValue, newValue) -> {
            logger.info("textShown changed to: " + newValue);
            question.setText(textShown.getValue());
        });



    }

}
