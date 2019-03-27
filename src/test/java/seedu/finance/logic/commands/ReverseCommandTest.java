package seedu.finance.logic.commands;

import static seedu.finance.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.finance.logic.commands.CommandTestUtil.showRecordAtIndex;
import static seedu.finance.testutil.TypicalIndexes.INDEX_FIRST_RECORD;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import org.junit.Test;
import seedu.finance.logic.CommandHistory;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand)
 * and unit tests for {@code ReverseCommand}.
 */
public class ReverseCommandTest {
    private Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_UnfilteredList_success(){
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.reverseFilteredRecordList();
        expectedModel.commitFinanceTracker();
        assertCommandSuccess(new ReverseCommand(), model, commandHistory, ReverseCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_FilteredList_success(){
        showRecordAtIndex(model, INDEX_FIRST_RECORD);
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.reverseFilteredRecordList();
        expectedModel.commitFinanceTracker();

        assertCommandSuccess(new ReverseCommand(), model, commandHistory, ReverseCommand.MESSAGE_SUCCESS, expectedModel);

    }

    /**
     * 1. Reverses the list
     * 2. Undo the reversion
     * 3. Redo the reversion.
     */
    @Test
    public void executeUndoRedo_unfilteredList_success(){
        Model expectedModel = new ModelManager(new FinanceTracker(model.getFinanceTracker()), new UserPrefs());
        expectedModel.reverseFilteredRecordList();
        expectedModel.commitFinanceTracker();

        ReverseCommand reverseCommand = new ReverseCommand();
        // reverse -> list is reversed
        reverseCommand.execute(model, commandHistory);

        // undo -> reverse financetracker back to previous state
        expectedModel.undoFinanceTracker();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> list is reversed again
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

    }
}

