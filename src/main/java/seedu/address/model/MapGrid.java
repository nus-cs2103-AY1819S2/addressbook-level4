package seedu.address.model;

import java.util.Arrays;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Cell;
import seedu.address.model.cell.Coordinates;
import seedu.address.model.cell.Status;

/**
 * Represents a mapGrid of a player. A {@code MapGrid} acts as a container of {@Cell} objects.
 */
public class MapGrid {

    private Cell[][] cellGrid;
    private int size;
    private BooleanProperty uiUpdateSwitch = new SimpleBooleanProperty();

    public MapGrid() {
        this.size = 0;
        cellGrid = new Cell[0][0];
    }

    public MapGrid(MapGrid mapGrid) {
        size = mapGrid.size;
        cellGrid = new Cell[mapGrid.getMapSize()][mapGrid.getMapSize()];
        copy2dArray(cellGrid, mapGrid.cellGrid);
    }

    // 2D map grid operations
    /**
     * Initialises the 2D Map from the given 2D Cell array
     * @param map to initialise the map from.
     */
    public void initialise(Cell[][] map) {
        this.size = map.length;

        cellGrid = new Cell[size][size];

        copy2dArray(cellGrid, map);
        updateUi();
    }

    /**
     * Returns a copy of the MapGrid in a 2D array format.
     * Any changes done to the copy will not affect the internal grid.
     * @return copy of the map.
     */
    public Cell[][] get2dArrayMapGridCopy() {
        Cell[][] mapCopy = new Cell[size][size];

        copy2dArray(mapCopy, cellGrid);

        return mapCopy;
    }

    /**
     * Returns a 2D array of {@code Status} which represents the view of this map from this map owner's perspective.
     * @return statuses of cells.
     */
    public Status[][] getPlayerMapView() {
        Status[][] playerMapView = new Status[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                playerMapView[i][j] = cellGrid[i][j].getStatus();
            }
        }
        return playerMapView;
    }

    /**
     * Returns a 2D array of {@code Status} which represents the view of this map from
     * the perspective of the enemy of this map's owner.
     * @return statuses of cells.
     */
    public Status[][] getEnemyMapView() {
        Status[][] enemyMapView = new Status[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Status cellStatus = cellGrid[i][j].getStatus();
                enemyMapView[i][j] = (cellStatus == Status.EMPTY || cellStatus == Status.SHIP)
                        ? Status.HIDDEN : cellStatus;
            }
        }
        return enemyMapView;
    }

    /**
     * Utility function to do a deep copy of a 2D array
     * @param output 2D array to copy to.
     * @param toBeCopied 2D array to copy from.
     */
    private void copy2dArray(Cell[][] output, Cell[][] toBeCopied) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                output[i][j] = new Cell(toBeCopied[i][j]);
            }
        }
    }

    /**
     * Returns map size.
     * @return map size.
     */
    public int getMapSize() {
        return cellGrid.length;
    }

    // UI operations
    /**
     * A listener will be added to this observable value in the UI.
     * Once this value changes the UI will be updated.
     * @return observableValue for the UI to listen to.
     */
    public ObservableBooleanValue getObservableValue() {
        return uiUpdateSwitch;
    }

    /**
     * Updates the UI.
     */
    public void updateUi() {
        if (uiUpdateSwitch.getValue() == false) {
            uiUpdateSwitch.setValue(true);
        } else {
            uiUpdateSwitch.setValue(false);
        }
    }

    //// cell-level operations
    /**
     * Returns the cell in the given coordinates.
     * @param coordinates of the cell.
     */
    private Cell getCell(Coordinates coordinates) {
        return cellGrid[coordinates.getRowIndex().getZeroBased()][coordinates.getColIndex().getZeroBased()];
    }

    /**
     * Returns the status of the specified {@code Cell}.
     * @param coord specifies which {@code Cell} to get {@code Status} from.
     * @return {@code Status} of the cell.
     */
    public Status getCellStatus(Coordinates coord) {
        return getCell(coord).getStatus();
    }

    /**
     * Returns the name of the {@code Battleship} in the specified {@code Cell}
     * @param coord specifies which {@code Cell} to get the name of {@code Battleship} from.
     * @return Name of the {@code Battleship} as a {@code String}.
     */
    public String getShipNameInCell(Coordinates coord) {
        return getCell(coord).getBattleship().get().toString();
    }

    /**
     * Attack a specified cell.
     * @param coordinates of the cell.
     * @return boolean which specifies hit or miss.
     */
    public boolean attackCell(Coordinates coordinates) throws ArrayIndexOutOfBoundsException {
        if (coordinates.getColIndex().getOneBased() > getMapSize()) {
            throw new ArrayIndexOutOfBoundsException("Coordinates are outside of the map");
        }

        boolean isSuccessfulHit =
                cellGrid[coordinates.getRowIndex().getZeroBased()][coordinates.getColIndex().getZeroBased()]
                        .receiveAttack();

        updateUi();
        return isSuccessfulHit;
    }

    /**
     * Put battleship on map grid.
     */
    public void putShip(Battleship battleship, Coordinates coordinates, Orientation orientation)
            throws ArrayIndexOutOfBoundsException {
        int rowIndexAsInt = coordinates.getRowIndex().getZeroBased();
        int colIndexAsInt = coordinates.getColIndex().getZeroBased();

        if ((coordinates.getColIndex().getOneBased() > getMapSize())
            || (coordinates.getRowIndex().getOneBased() > getMapSize())) {
            throw new ArrayIndexOutOfBoundsException("Coordinates are outside of the map");
        }

        int rowInt = rowIndexAsInt;
        int colInt = colIndexAsInt;

        for (int i = 0; i < battleship.getLength(); i++) {
            if (orientation.isHorizontal()) {
                colInt = colIndexAsInt + i;
            } else {
                rowInt = rowIndexAsInt + i;
            }

            cellGrid[rowInt][colInt].putShip(battleship);
        }
    }

    //// util methods
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MapGrid // instanceof handles nulls
                && Arrays.deepEquals(cellGrid, ((MapGrid) other).cellGrid));
    }

    @Override
    public int hashCode() {
        return cellGrid.hashCode();
    }
}
