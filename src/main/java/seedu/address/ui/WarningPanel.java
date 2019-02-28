package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * A ui for the warning panel that is displayed at the right of the application.
 */
public class WarningPanel extends UiPart<Region> {
    private static final String FXML = "WarningPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(WarningPanel.class);

    @FXML
    private VBox warningVBox;

    public WarningPanel() {
        super(FXML);

        createGrid();

    }

    private void createGrid() {
        warningVBox.getChildren().addAll(new Label("Expiring Soon"), new WarningContentCell().getRoot());
        warningVBox.getChildren().addAll(new Label("Low in Stock"), new WarningContentCell().getRoot());
    }

}