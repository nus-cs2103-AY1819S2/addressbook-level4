package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstHealthWorker;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstRequest;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalHealthWorkerBook(),
            getTypicalRequestBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalHealthWorkerBook(),
            getTypicalRequestBook(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstHealthWorker(model);
        deleteFirstRequest(model);
        model.undo();
        model.undo();

        deleteFirstHealthWorker(expectedModel);
        deleteFirstRequest(expectedModel);
        expectedModel.undo();
        expectedModel.undo();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redo();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
