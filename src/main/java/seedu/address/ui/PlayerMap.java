package seedu.address.ui;

import javafx.beans.value.ObservableBooleanValue;
import javafx.scene.paint.Color;
import seedu.address.model.MapGrid;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Status;

/**
 * Panel containing the list of persons.
 */
public class PlayerMap extends Map {

    public PlayerMap(ObservableBooleanValue modelUpdateObservable, MapGrid mapGrid) {
        super(modelUpdateObservable, mapGrid);
    }

    /**
     * Determine color of cell from the status of cell
     */
    @Override
    protected Color getColor(Cell cell) {
        Status status = cell.getStatus();
        switch (status) {
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
}
