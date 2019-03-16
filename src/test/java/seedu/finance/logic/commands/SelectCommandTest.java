package seedu.finance.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.finance.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.finance.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.finance.logic.commands.CommandTestUtil.showRecordAtIndex;
import static seedu.finance.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.finance.testutil.TypicalIndexes.INDEX_SECOND_RECORD;
import static seedu.finance.testutil.TypicalIndexes.INDEX_THIRD_RECORD;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import org.junit.Test;

import seedu.finance.commons.core.Messages;
import seedu.finance.commons.core.index.Index;
import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastRecordIndex = Index.fromOneBased(model.getFilteredRecordList().size());

        assertExecutionSuccess(INDEX_FIRST_RECORD);
        assertExecutionSuccess(INDEX_THIRD_RECORD);
        assertExecutionSuccess(lastRecordIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredRecordList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);
        showRecordAtIndex(expectedModel, INDEX_FIRST_RECORD);

        assertExecutionSuccess(INDEX_FIRST_RECORD);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showRecordAtIndex(model, INDEX_FIRST_RECORD);
        showRecordAtIndex(expectedModel, INDEX_FIRST_RECORD);

        Index outOfBoundsIndex = INDEX_SECOND_RECORD;
        // ensures that outOfBoundIndex is still in bounds of finance tracker list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getFinanceTracker().getRecordList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_RECORD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_RECORD);
        SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_RECORD);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_RECORD);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different record -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index},
     * and checks that the model's selected record is set to the record at {@code index} in the filtered record list.
     */
    private void assertExecutionSuccess(Index index) {
        SelectCommand selectCommand = new SelectCommand(index);
        String expectedMessage = String.format(SelectCommand.MESSAGE_SELECT_RECORD_SUCCESS, index.getOneBased());
        expectedModel.setSelectedRecord(model.getFilteredRecordList().get(index.getZeroBased()));

        assertCommandSuccess(selectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectCommand selectCommand = new SelectCommand(index);
        assertCommandFailure(selectCommand, model, commandHistory, expectedMessage);
    }
}
