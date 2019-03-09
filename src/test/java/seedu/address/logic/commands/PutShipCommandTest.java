package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_FIRST_CELL;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_FIRST_CELL_NEXT_HORIZONTAL;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_FIRST_CELL_NEXT_VERTICAL;
import static seedu.address.testutil.TypicalIndexes.COORDINATES_LAST_CELL;
import static seedu.address.testutil.TypicalIndexes.MAP_SIZE_TEN;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.battleship.Battleship;
import seedu.address.model.cell.Coordinates;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class PutShipCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_battleshipAlreadyPresent_failure() {
        model.getMapGrid().initialise(MAP_SIZE_TEN);
        Battleship battleship = new Battleship();
        model.getMapGrid().getCell(COORDINATES_FIRST_CELL).putShip(battleship);

        PutShipCommand putShipCommand = new PutShipCommand(COORDINATES_FIRST_CELL, battleship);

        assertCommandFailure(putShipCommand, model, commandHistory,
                PutShipCommand.MESSAGE_BATTLESHIP_PRESENT_BODY_VERTICAL);
    }

    @Test
    public void execute_putBattleshipVertical_failure() {
        model.getMapGrid().initialise(MAP_SIZE_TEN);
        Battleship battleship = new Battleship();
        PutShipCommand putShipCommand = new PutShipCommand(COORDINATES_FIRST_CELL, battleship);
        model.getMapGrid().getCell(COORDINATES_FIRST_CELL_NEXT_VERTICAL).putShip(battleship);

        assertCommandFailure(putShipCommand, model, commandHistory,
                PutShipCommand.MESSAGE_BATTLESHIP_PRESENT_BODY_VERTICAL);
    }

    @Test
    public void execute_putBattleshipHorizontal_failure() {
        model.getMapGrid().initialise(MAP_SIZE_TEN);
        Battleship battleship = new Battleship();
        PutShipCommand putShipCommand = new PutShipCommand(COORDINATES_FIRST_CELL, battleship);
        model.getMapGrid().getCell(COORDINATES_FIRST_CELL_NEXT_HORIZONTAL).putShip(battleship);

        assertCommandFailure(putShipCommand, model, commandHistory,
                PutShipCommand.MESSAGE_BATTLESHIP_PRESENT_BODY_HORIZONTAL);
    }

    @Test
    public void equals() {
        final PutShipCommand standardCommand = new PutShipCommand(COORDINATES_FIRST_CELL, new Battleship());

        // same values -> returns true
        PutShipCommand commandWithSameValues = new PutShipCommand(new Coordinates("a1"), new Battleship());
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different coordinates -> returns false
        assertFalse(standardCommand.equals(new PutShipCommand(COORDINATES_LAST_CELL, new Battleship())));

    }

}
