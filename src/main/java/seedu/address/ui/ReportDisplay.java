package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;

/**
 * An UI component when user enters a test session.
 */
public class ReportDisplay extends UiPart<Region> {

    private static final String FXML = "ReportDisplay.fxml";

    @FXML
    private GridPane reportDisplay;

    @FXML
    private Label message;

    public ReportDisplay() {
        super(FXML);
        reportDisplay.getChildren().clear();
        message.setText("Hello fucking.");
        reportDisplay.getChildren().add(message);

    }
}

