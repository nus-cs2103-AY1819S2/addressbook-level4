package seedu.address.model.cell;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.battleship.Battleship;

public class CellTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void status() {
        Cell cell = new Cell();
        assertEquals(cell.getStatus(), Status.EMPTY);

        cell.receiveAttack();
        assertEquals(cell.getStatus(), Status.EMPTYHIT);

        Cell battleShipCell = new Cell();
        battleShipCell.putShip(new Battleship());
        assertEquals(battleShipCell.getStatus(), Status.SHIP);

        battleShipCell.receiveAttack();
        assertEquals(battleShipCell.getStatus(), Status.SHIPHIT);

        battleShipCell.receiveAttack();
        assertEquals(battleShipCell.getStatus(), Status.DESTROYED);
    }

    @Test
    public void copyConstructor() {
        Cell emptyCell = new Cell();
        Cell copyCell = new Cell(emptyCell);
        assertEquals(emptyCell.getBattleship(), copyCell.getBattleship());

        Cell battleShipCell = new Cell();
        battleShipCell.putShip(new Battleship());
        copyCell = new Cell(battleShipCell);

        assertEquals(battleShipCell.getBattleship(), copyCell.getBattleship());
    }

    @Test
    public void equals() {
    }
}
