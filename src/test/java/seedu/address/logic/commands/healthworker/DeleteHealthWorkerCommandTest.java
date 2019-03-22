package seedu.address.logic.commands.healthworker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.DeleteHealthWorkerCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.healthworker.HealthWorker;

public class DeleteHealthWorkerCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
        getTypicalPatientBook(), getTypicalRequestBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndex() {
        HealthWorker toDelete = model.getFilteredHealthWorkerList().get(INDEX_FIRST.getZeroBased());
        DeleteHealthWorkerCommand deleteHealthWorkerCommand = new DeleteHealthWorkerCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteHealthWorkerCommand.MESSAGE_DELETE_HEALTHWORKER_SUCCESS, toDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getHealthWorkerBook(),
            model.getPatientBook(), model.getRequestBook(), new UserPrefs());
        expectedModel.deleteHealthWorker(toDelete);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteHealthWorkerCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredHealthWorkerList().size() + 1);
        DeleteHealthWorkerCommand deleteHealthWorkerCommand = new DeleteHealthWorkerCommand(outOfBoundIndex);

        assertCommandFailure(deleteHealthWorkerCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    // TODO: add tests for undo/redo after undo/redo addressbook methods are implemented

    @Test
    public void equals() {
        DeleteHealthWorkerCommand deleteFirstCommand = new DeleteHealthWorkerCommand(INDEX_FIRST);
        DeleteHealthWorkerCommand deleteSecondCommand = new DeleteHealthWorkerCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> return true
        DeleteHealthWorkerCommand deleteFirstCommandCopy = new DeleteHealthWorkerCommand(INDEX_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered HealthWorker list to show no one.
     */
    private void showNoHealthWorker(Model model) {
        model.updateFilteredHealthWorkerList(p -> false);

        assertTrue(model.getFilteredHealthWorkerList().isEmpty());
    }
}
