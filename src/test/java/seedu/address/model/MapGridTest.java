package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.SizeTenMapGrid.getSizeTenMapGrid;
import static seedu.address.testutil.TypicalPersons.ALICE;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;

import seedu.address.model.battleship.Battleship;
import seedu.address.model.battleship.Orientation;
import seedu.address.model.cell.Coordinates;

public class MapGridTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final MapGrid mapGrid = new MapGrid();

    @Test
    public void putShipTest() {
        MapGrid sizeTenMap = getSizeTenMapGrid();
        Battleship battleship = new Battleship();
        Orientation orientation = new Orientation("vertical");

        sizeTenMap.putShip(battleship, new Coordinates("a1"), orientation);

        assertEquals(sizeTenMap.get2dArrayMapGridCopy()[0][0].getBattleship().get(), battleship);
    }

    @Test
    public void attackCellTest() {
        MapGrid sizeTenmap = getSizeTenMapGrid();
        Battleship battleship = new Battleship();
        Orientation orientation = new Orientation("vertical");
        assertFalse(sizeTenmap.attackCell(new Coordinates("a1")));

        sizeTenmap.putShip(battleship, new Coordinates("a1"), orientation);
        assertTrue(sizeTenmap.attackCell(new Coordinates("a1")));
    }

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), mapGrid.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        mapGrid.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        MapGrid newData = new MapGrid();
        mapGrid.resetData(newData);
        assertEquals(newData, mapGrid);
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        mapGrid.getPersonList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        mapGrid.addListener(listener);
        mapGrid.addPerson(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        mapGrid.addListener(listener);
        mapGrid.removeListener(listener);
        mapGrid.addPerson(ALICE);
        assertEquals(0, counter.get());
    }
}
