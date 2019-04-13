package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRestOrRant.TABLE6;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.table.Table;
import seedu.address.testutil.TableBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SpaceForCommand.
 */
public class SpaceForCommandTest {

    private Model model = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_tableUpdatedByModel_updateSuccessful() {
        Model expectedModel = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
        Table editedTable = new TableBuilder(TABLE6).withTableStatus("3/3").build();
        SpaceForCommand spaceForCommand = new SpaceForCommand(3);
        expectedModel.setTable(TABLE6, editedTable);
        assertCommandSuccess(Mode.RESTAURANT_MODE, spaceForCommand, model, commandHistory,
                new CommandResult(String.format(SpaceForCommand.MESSAGE_SUCCESS,
                        editedTable.getTableStatus().getNumberOfTakenSeats(), editedTable.getTableNumber())),
                expectedModel);
    }

    @Test
    public void execute_numberOfCustomersTooLarge_updateFailure() {
        SpaceForCommand spaceForCommand = new SpaceForCommand(10); // Larger than maximum fit for available tables
        assertCommandFailure(Mode.RESTAURANT_MODE, spaceForCommand, model, commandHistory,
                String.format(SpaceForCommand.MESSAGE_NO_AVAILABLE_TABLE, "10"));
    }

    @Test
    public void equals() {
        final SpaceForCommand standardCommand = new SpaceForCommand(3);

        // same values -> returns true
        SpaceForCommand editedSpaceForCommand = new SpaceForCommand(3);
        assertTrue(standardCommand.equals(editedSpaceForCommand));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different number of seats -> returns false
        editedSpaceForCommand = new SpaceForCommand(4);
        assertFalse(standardCommand.equals(editedSpaceForCommand));
    }

}
