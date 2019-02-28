package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

/**
 * Cell containing content of warning.
 */
public class WarningContentCell extends UiPart<Region> {
    private static final String FXML = "WarningContentCell.fxml";
    private final Logger logger = LogsCenter.getLogger(WarningContentCell.class);

    @FXML
    private ListView<String> dummyListView;

    public WarningContentCell() {
        super(FXML);

        // disable selection of items in ListView
        dummyListView.setMouseTransparent(true);
        dummyListView.setFocusTraversable(false);

        // placeholder if the list is empty
        dummyListView.setPlaceholder(new Label("Nothing to show"));

        ObservableList<String>  dummyList = FXCollections.observableArrayList ("dummy1", "dummy2", "dummy3");
        dummyListView.setItems(dummyList);
    }

}
