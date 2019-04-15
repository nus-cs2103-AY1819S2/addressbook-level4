package seedu.equipment.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalEquipmentManager;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_FIRST_WORKLIST;
import static seedu.equipment.testutil.TypicalIndexes.INDEX_SECOND_WORKLIST;

import org.junit.Test;

import seedu.equipment.commons.core.index.Index;
import seedu.equipment.logic.CommandHistory;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;
import seedu.equipment.model.UserPrefs;
import seedu.equipment.model.WorkList;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteWorkListCommand}.
 */
public class DeleteWorkListCommandTest {

    private Model model = new ModelManager(getTypicalEquipmentManager(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        WorkList workListToDelete = model.getFilteredWorkListList().get(INDEX_FIRST_WORKLIST.getZeroBased());
        DeleteWorkListCommand deleteWorkListCommand = new DeleteWorkListCommand(INDEX_FIRST_WORKLIST);

        String expectedMessage = String.format(DeleteWorkListCommand.MESSAGE_DELETE_WORKLIST_SUCCESS, workListToDelete);

        ModelManager expectedModel = new ModelManager(model.getEquipmentManager(), new UserPrefs());
        expectedModel.deleteWorkList(workListToDelete);
        expectedModel.commitEquipmentManager();

        assertCommandSuccess(deleteWorkListCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredWorkListList().size() + 1);
        DeleteWorkListCommand deleteWorkListCommand = new DeleteWorkListCommand(outOfBoundIndex);

        assertCommandFailure(deleteWorkListCommand, model, commandHistory,
                DeleteWorkListCommand.MESSAGE_INVALID_WORKLIST_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteWorkListCommand deleteFirstCommand = new DeleteWorkListCommand(INDEX_FIRST_WORKLIST);
        DeleteWorkListCommand deleteSecondCommand = new DeleteWorkListCommand(INDEX_SECOND_WORKLIST);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteWorkListCommand deleteFirstCommandCopy = new DeleteWorkListCommand(INDEX_FIRST_WORKLIST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different work list -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }
}
