package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.table.ReadOnlyTables;
import seedu.address.model.table.Table;

/**
 * The Browser Panel for the menu.
 */

public class TableFlowPanel extends UiPart<Region> {

    private static final String FXML = "TableFlowPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(TableFlowPanel.class);

    @FXML
    private FlowPane tableFlowPane;

    public TableFlowPanel(ObservableList<Table> tableObservableList, ReadOnlyTables tables) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded FlowPane.
        getRoot().setOnKeyPressed(Event::consume);

        tableFlowPane.setHgap(1);
        tableFlowPane.setVgap(1);
        tableFlowPane.setPrefWrapLength(404);

        // Creates a FlowPane for each Table and adds to the list of FLowPane 
        for (Table table : tableObservableList) {
            tableFlowPane.getChildren().add(new TableCard(table).getRoot());
        }
    }

}
