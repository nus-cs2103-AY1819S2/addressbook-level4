package seedu.knowitall.logic.commands;

import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.logic.commands.CommandTestUtil.deleteFirstCard;
import static seedu.knowitall.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Before;
import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.testutil.TypicalIndexes;

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        deleteFirstCard(model);
        deleteFirstCard(model);
        model.undoActiveCardFolder();
        model.undoActiveCardFolder();

        expectedModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        deleteFirstCard(expectedModel);
        deleteFirstCard(expectedModel);
        expectedModel.undoActiveCardFolder();
        expectedModel.undoActiveCardFolder();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoActiveCardFolder();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoActiveCardFolder();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
