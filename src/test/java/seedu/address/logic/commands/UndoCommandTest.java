package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstFlashcard;
import static seedu.address.testutil.TypicalFlashcards.getTypicalCardCollection;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalCardCollection(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalCardCollection(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();
    private final String deleteFirstUndoMessage = String.format(UndoCommand.MESSAGE_SUCCESS, "delete 1");

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstFlashcard(model);
        deleteFirstFlashcard(model);

        deleteFirstFlashcard(expectedModel);
        deleteFirstFlashcard(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoCardCollection();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, deleteFirstUndoMessage, expectedModel);

        // single undoable state in model
        expectedModel.undoCardCollection();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, deleteFirstUndoMessage, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
