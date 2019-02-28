package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * A ui for the warning panel that is displayed at the right of the application.
 */
public class WarningPanel extends UiPart<Region> {
    private static final int ROWS = 2; // one for expiry dates, one for stock
    private static final String FXML = "WarningPanel.fxml";
    private static final Logger logger = LogsCenter.getLogger(WarningPanel.class);

    @FXML
    private GridPane warningGridPane;

    public WarningPanel() {
        super(FXML);

        createGrid();

    }

    private void createGrid() {
//        warningGridPane.add(new Pane(new Label("Expiring Soon")), 0, 0);
//        warningGridPane.add(new Pane(new Label("Low in Stock")), 0, 1);
        warningGridPane.add(new WarningContentCell().getRoot(), 0, 0);
        warningGridPane.add(new WarningContentCell().getRoot(), 0, 1);
        warningGridPane.setVgap(150.0);
    }

}