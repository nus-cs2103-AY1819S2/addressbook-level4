package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMedicineAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEDICINE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEDICINE;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.medicine.Medicine;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalInventory(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Medicine medicineToDelete = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MEDICINE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MEDICINE_SUCCESS, medicineToDelete);

        ModelManager expectedModel = new ModelManager(model.getInventory(), new UserPrefs());
        expectedModel.deleteMedicine(medicineToDelete);
        expectedModel.commitInventory();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);

        Medicine medicineToDelete = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MEDICINE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_MEDICINE_SUCCESS, medicineToDelete);

        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs());
        expectedModel.deleteMedicine(medicineToDelete);
        expectedModel.commitInventory();
        showNoMedicine(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showMedicineAtIndex(model, INDEX_FIRST_MEDICINE);

        Index outOfBoundIndex = INDEX_SECOND_MEDICINE;
        // ensures that outOfBoundIndex is still in bounds of inventory list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getInventory().getMedicineList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Medicine medicineToDelete = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MEDICINE);
        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs());
        expectedModel.deleteMedicine(medicineToDelete);
        expectedModel.commitInventory();

        // delete -> first medicine deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts Inventory back to previous state and filtered medicine list to show all medicines
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first medicine deleted again
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMedicineList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> inventory state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEDICINE_DISPLAYED_INDEX);

        // single inventory state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Medicine} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted medicine in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the medicine object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameMedicineDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_MEDICINE);
        Model expectedModel = new ModelManager(model.getInventory(), new UserPrefs());

        showMedicineAtIndex(model, INDEX_SECOND_MEDICINE);
        Medicine medicineToDelete = model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased());
        expectedModel.deleteMedicine(medicineToDelete);
        expectedModel.commitInventory();

        // delete -> deletes second medicine in unfiltered medicine list / first medicine in filtered medicine list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts Inventory back to previous state and filtered medicine list to show all medicines
        expectedModel.undoInventory();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(medicineToDelete, model.getFilteredMedicineList().get(INDEX_FIRST_MEDICINE.getZeroBased()));
        // redo -> deletes same second medicine in unfiltered medicine list
        expectedModel.redoInventory();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_MEDICINE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_MEDICINE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_MEDICINE);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different medicine -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoMedicine(Model model) {
        model.updateFilteredMedicineList(p -> false);

        assertTrue(model.getFilteredMedicineList().isEmpty());
    }
}
