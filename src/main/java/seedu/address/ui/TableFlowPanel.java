package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
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
