package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
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

        createVBox();

    }

    /**
     * Creates the VBox which holds the lists.
     */
    private void createVBox() {
        warningVBox.getChildren().addAll(new Label("Expiring Soon"), new WarningContentCell().getRoot());
        warningVBox.getChildren().addAll(new Label("Low in Stock"), new WarningContentCell().getRoot());
    }

}