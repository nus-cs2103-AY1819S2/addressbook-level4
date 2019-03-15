package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.MapGrid;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Status;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private VBox grid;

    public PersonListPanel(ObservableList<Cell> cellList, ObservableValue<Cell> selectedPerson,
                           Consumer<Cell> onSelectedPersonChange, ObservableBooleanValue modelUpdateObservable,
                           MapGrid mapGrid) {
        super(FXML);
        modelUpdateObservable.addListener(observable -> {
            int size = mapGrid.getMapSize();
            Cell[][] mapArray = mapGrid.get2dArrayMapGridCopy();

            grid.getChildren().clear();

            for (int i = 0; i < size; i++) {
                HBox row = new HBox();
                for (int j = 0; j < size; j++) {
                    Rectangle cell = new Rectangle(30, 30);
                    Cell mapCell = mapArray[i][j];

                    Color color = getColor(mapCell);
                    cell.setStroke(Color.BLACK);
                    cell.setFill(color);

                    Text text = new Text("");
                    StackPane sp = new StackPane();
                    sp.getChildren().addAll(cell, text);
                    row.getChildren().add(sp);
                }
                grid.getChildren().add(row);
            }
        });
    }

    /**
     * Determine color of cell from the status of cell
     */
    Color getColor(Cell cell) {
        Status status = cell.getStatus();
        switch(status) {
        case SHIP:
            return Color.BLACK;
        case SHIPHIT:
            return Color.ORANGE;
        case EMPTY:
            return Color.LIGHTBLUE;
        case EMPTYHIT:
            return Color.DARKBLUE;
        case DESTROYED:
            return Color.RED;
        default:
            return Color.WHITE;
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Cell} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Cell> {
        @Override
        protected void updateItem(Cell cell, boolean empty) {
            super.updateItem(cell, empty);

            if (empty || cell == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(cell, getIndex() + 1).getRoot());
            }
        }
    }

}
