package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EditPaxCommand.MESSAGE_SUCCESS;
import static seedu.address.testutil.TypicalRestOrRant.TABLE1;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.Mode;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.table.Table;
import seedu.address.model.table.TableStatus;
import seedu.address.testutil.TableBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditPaxCommand.
 */
public class EditPaxCommandTest {

    private Model model = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_tableUpdatedByModel_updateSuccessful() {
        String[] tableStatusInString = new String[]{"1", "0"};
        Table editedTable = new TableBuilder(TABLE1).withTableStatus("0/4").build();
        EditPaxCommand editPaxCommand = new EditPaxCommand(tableStatusInString);
        Model expectedModel = new ModelManager(getTypicalRestOrRant(), new UserPrefs());
        expectedModel.setTable(TABLE1, editedTable);
        expectedModel.clearOrderItemsFrom(TABLE1.getTableNumber());
        assertCommandSuccess(Mode.RESTAURANT_MODE, editPaxCommand, model, commandHistory,
                new CommandResult(String.format(MESSAGE_SUCCESS, editedTable.getTableNumber(),
                        editedTable.getTableStatus())), expectedModel);
    }

    @Test
    public void execute_invalidTableNumber_updateFailure() {
        String[] invalidTableStatusInString = new String[]{"9", "4"}; // Table 9 does not exist
        EditPaxCommand editPaxCommand = new EditPaxCommand(invalidTableStatusInString);
        assertCommandFailure(Mode.RESTAURANT_MODE, editPaxCommand, model, commandHistory,
                String.format(EditPaxCommand.MESSAGE_INVALID_TABLE_NUMBER, "9"));
    }

    @Test
    public void execute_tableStatusTooLarge_updateFailure() {
        String[] invalidTableStatusInString = new String[]{"1", "6"}; // Max capacity of table is only 4
        EditPaxCommand editPaxCommand = new EditPaxCommand(invalidTableStatusInString);
        assertCommandFailure(Mode.RESTAURANT_MODE, editPaxCommand, model, commandHistory,
                String.format(TableStatus.MESSAGE_INVALID_NUMBER_OF_CUSTOMERS,
                        TABLE1.getTableStatus().getNumberOfTakenSeats()));
    }

    @Test
    public void execute_tableStatusNoChange_updateFailure() {
        String[] invalidTableStatusInString = new String[]{"1", "4"}; // Same table status as current (4)
        EditPaxCommand editPaxCommand = new EditPaxCommand(invalidTableStatusInString);
        assertCommandFailure(Mode.RESTAURANT_MODE, editPaxCommand, model, commandHistory,
                String.format(EditPaxCommand.MESSAGE_NO_CHANGE_IN_STATUS, TABLE1.getTableNumber(),
                        TABLE1.getTableStatus()));
    }

    @Test
    public void equals() {
        String[] tableStatusInString = new String[]{"1", "4"};
        final EditPaxCommand standardCommand = new EditPaxCommand(tableStatusInString);

        // same values -> returns true
        EditPaxCommand editedEditPaxCommand = new EditPaxCommand(tableStatusInString);
        assertTrue(standardCommand.equals(editedEditPaxCommand));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different table number -> returns false
        tableStatusInString = new String[]{"0", "4"};
        editedEditPaxCommand = new EditPaxCommand(tableStatusInString);
        assertFalse(standardCommand.equals(editedEditPaxCommand));

        // different number of taken seats -> returns false
        tableStatusInString = new String[]{"1", "5"};
        editedEditPaxCommand = new EditPaxCommand(tableStatusInString);
        assertFalse(standardCommand.equals(editedEditPaxCommand));
    }

}
