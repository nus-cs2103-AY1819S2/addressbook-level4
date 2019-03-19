package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
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



    public QuestionPanel() {
        super(FXML);

    }

}
