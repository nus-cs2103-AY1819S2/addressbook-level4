package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import static seedu.address.logic.commands.CommandTestUtil.showApparelAtIndex;
import static seedu.address.testutil.TypicalApparels.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPAREL;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPAREL;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.apparel.Apparel;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Apparel apparelToDelete = model.getFilteredApparelList().get(INDEX_FIRST_APPAREL.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_APPAREL);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, apparelToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteApparel(apparelToDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApparelList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_APPAREL_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showApparelAtIndex(model, INDEX_FIRST_APPAREL);

        Apparel apparelToDelete = model.getFilteredApparelList().get(INDEX_FIRST_APPAREL.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_APPAREL);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, apparelToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteApparel(apparelToDelete);
        expectedModel.commitAddressBook();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showApparelAtIndex(model, INDEX_FIRST_APPAREL);

        Index outOfBoundIndex = INDEX_SECOND_APPAREL;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getApparelList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        System.out.println("Execute invalid test function");
        for (Apparel a : model.getFilteredApparelList()) {
            System.out.println(a.toString());
        }

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_APPAREL_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Apparel apparelToDelete = model.getFilteredApparelList().get(INDEX_FIRST_APPAREL.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_APPAREL);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteApparel(apparelToDelete);
        expectedModel.commitAddressBook();

        // delete -> first apparel deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered apparel list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first apparel deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredApparelList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_APPAREL_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Apparel} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted apparel in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the apparel object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_APPAREL);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showApparelAtIndex(model, INDEX_SECOND_APPAREL);
        Apparel apparelToDelete = model.getFilteredApparelList().get(INDEX_FIRST_APPAREL.getZeroBased());
        expectedModel.deleteApparel(apparelToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second apparel in unfiltered apparel list / first apparel in filtered apparel list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered apparel list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        System.out.println("apparelToDelete = " + apparelToDelete.toString());
        System.out.println("filteredItem = " + model.getFilteredApparelList()
                .get(INDEX_FIRST_APPAREL.getZeroBased()).toString());
        assertNotEquals(apparelToDelete, model.getFilteredApparelList().get(INDEX_FIRST_APPAREL.getZeroBased()));
        // redo -> deletes same second apparel in unfiltered apparel list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_APPAREL);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_APPAREL);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_APPAREL);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different apparel -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredApparelList(p -> false);

        assertTrue(model.getFilteredApparelList().isEmpty());
    }
}
