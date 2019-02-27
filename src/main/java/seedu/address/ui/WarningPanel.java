package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

import java.util.logging.Logger;

/**
 * A ui for the warning panel that is displayed at the right of the application.
 */
public class WarningPanel extends UiPart<Region> {
    @FXML
    private ListView<String> dummyList;
    private final Logger logger = LogsCenter.getLogger(WarningPanel.class);

    private static final String FXML = "WarningPanel.fxml";

    public WarningPanel() {
        super(FXML);
        dummyList.setPlaceholder(new Label("No Content In List"));
        ObservableList<String> names = FXCollections.observableArrayList(
                "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");

        dummyList.setItems(names);

    }

}