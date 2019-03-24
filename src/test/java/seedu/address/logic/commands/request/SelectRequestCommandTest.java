package seedu.address.logic.commands.request;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

class SelectRequestCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
        getTypicalRequestBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
        getTypicalRequestBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndex_UnfilteredList_success() {
        Index lastRequestIndex = Index.fromOneBased(model.getFilteredRequestList().size());

        assertExecutionSuccess(INDEX_FIRST);
        assertExecutionSuccess(INDEX_THIRD);
        assertExecutionSuccess(lastRequestIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredRequestList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX);
    }

    // TODO show request at index - Hui Chun

    @Test
    public void equals() {
        SelectRequestCommand selectFirstCommand = new SelectRequestCommand(INDEX_FIRST);
        SelectRequestCommand selectSecondCommand = new SelectRequestCommand(INDEX_SECOND);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectRequestCommand selectFirstCommandCopy = new SelectRequestCommand(INDEX_FIRST);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectRequestCommand} with the given {@code index},
     * and checks that the model's selected request is set to the request at {@code index} in the
     * filtered request list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectRequestCommand selectCommand = new SelectRequestCommand(index);
        String expectedMessage = String.format(SelectRequestCommand.MESSAGE_SELECT_REQUEST_SUCCESS,
            index.getOneBased());
        expectedModel.setSelectedRequest(model.getFilteredRequestList().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectRequestCommand} with the given {@code index}, and checks that a
     * {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectRequestCommand selectCommand = new SelectRequestCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }
}