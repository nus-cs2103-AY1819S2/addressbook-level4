package seedu.finance.logic.commands;

import static seedu.finance.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.finance.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.finance.logic.commands.CommandTestUtil.deleteFirstRecord;
import static seedu.finance.testutil.TypicalRecords.getTypicalFinanceTracker;

import org.junit.Before;
import org.junit.Test;

import seedu.finance.logic.CommandHistory;
import seedu.finance.model.Model;
import seedu.finance.model.ModelManager;
import seedu.finance.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalFinanceTracker(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstRecord(model);
        deleteFirstRecord(model);
        model.undoFinanceTracker();
        model.undoFinanceTracker();

        deleteFirstRecord(expectedModel);
        deleteFirstRecord(expectedModel);
        expectedModel.undoFinanceTracker();
        expectedModel.undoFinanceTracker();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoFinanceTracker();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
