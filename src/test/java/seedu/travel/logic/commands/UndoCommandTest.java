package seedu.travel.logic.commands;

import static seedu.travel.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.travel.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.travel.logic.commands.CommandTestUtil.deleteFirstPlace;
import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

import org.junit.Before;
import org.junit.Test;

import seedu.travel.logic.CommandHistory;
import seedu.travel.model.Model;
import seedu.travel.model.ModelManager;
import seedu.travel.model.UserPrefs;

public class UndoCommandTest {

    private final Model model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of models' undo/redo history
        deleteFirstPlace(model);
        deleteFirstPlace(model);

        deleteFirstPlace(expectedModel);
        deleteFirstPlace(expectedModel);
    }

    @Test
    public void execute() {
        // multiple undoable states in model
        expectedModel.undoTravelBuddy();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // single undoable state in model
        expectedModel.undoTravelBuddy();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // no undoable states in model
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
    }
}
