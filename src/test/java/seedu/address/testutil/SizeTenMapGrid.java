package seedu.address.testutil;

import seedu.address.model.MapGrid;
import seedu.address.model.cell.Cell;

/**
 * A utility class that returns an empty size 10 grid
 */
public class SizeTenMapGrid {
    private static final int SIZE_TEN = 10;

    /**
     * Returns a size ten map
     */
    public static MapGrid getSizeTenMapGrid() {
        Cell[][] cellGrid = new Cell[SIZE_TEN][SIZE_TEN];
        for (int i = 0; i < SIZE_TEN; i++) {
            for (int j = 0; j < SIZE_TEN; j++) {
                cellGrid[i][j] = new Cell();
            }
        }

        MapGrid newMap = new MapGrid();
        newMap.initialise(cellGrid);
        return newMap;
    }
}
