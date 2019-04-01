package seedu.address.logic.commands.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.request.Request;

class DeleteRequestCommandTest {

    private Model model = new ModelManager(getTypicalHealthWorkerBook(),
        getTypicalRequestBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @org.junit.jupiter.api.Test
    public void execute_validIndex() {
        Request toDelete = model.getFilteredRequestList().get(INDEX_FIRST.getZeroBased());
        DeleteRequestCommand deleteRequestCommand = new DeleteRequestCommand(INDEX_FIRST);

        String expectedMessage =
            String.format(DeleteRequestCommand.MESSAGE_DELETE_REQUEST_SUCCESS, toDelete);

        ModelManager expectedModel = new ModelManager(model.getHealthWorkerBook(),
                model.getRequestBook(), new UserPrefs());
        expectedModel.deleteRequest(toDelete);
        expectedModel.commitRequestBook();

        assertCommandSuccess(deleteRequestCommand, model, commandHistory, expectedMessage,
            expectedModel);

    }

    @Test
    public void execute_invalidIndex() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRequestList().size() + 1);
        DeleteRequestCommand deleteRequestCommand = new DeleteRequestCommand(outOfBoundIndex);

        assertCommandFailure(deleteRequestCommand, model, commandHistory, Messages
            .MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteRequestCommand deleteFirstCommand = new DeleteRequestCommand(INDEX_FIRST);
        DeleteRequestCommand deleteSecondCommand = new DeleteRequestCommand(INDEX_SECOND);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> return true
        DeleteRequestCommand deleteRequestCommand = new DeleteRequestCommand(INDEX_FIRST);
        assertEquals(deleteFirstCommand, deleteRequestCommand);

        // different types -> returns false
        assertNotEquals(deleteFirstCommand, 1);

        // null -> returns false
        assertNotEquals(deleteFirstCommand, null);

        // different request -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }

}
