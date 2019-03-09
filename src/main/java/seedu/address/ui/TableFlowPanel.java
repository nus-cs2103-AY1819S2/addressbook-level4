package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.table.ReadOnlyTables;
import seedu.address.model.table.Table;

/**
 * The Browser Panel for the menu.
 */

public class TableFlowPanel extends UiPart<Region> {

    private static final String FXML = "TableFlowPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private FlowPane tableFlowPane;

    // TODO: constructors for different modes
    public TableFlowPanel(ObservableList<Table> tableObservableList, ReadOnlyTables tables) {
        super(FXML);

        // To prevent triggering events for typing inside the loaded FlowPane.
        getRoot().setOnKeyPressed(Event::consume);

        tableFlowPane.setOrientation(Orientation.HORIZONTAL);
        tableFlowPane.setHgap(0);
        tableFlowPane.setVgap(0);
        tableFlowPane.setAlignment(Pos.TOP_LEFT);
        for (Table table : tableObservableList) {
            tableFlowPane.getChildren().add(new TableFlowPane(table));
        }
    }

    class TableFlowPane extends StackPane {

        private final TableCard tableCard;

        public TableFlowPane(Table table) {
            tableCard = new TableCard(table);
        }
    }

}
