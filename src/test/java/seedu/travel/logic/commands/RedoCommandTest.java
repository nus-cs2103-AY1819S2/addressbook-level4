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

public class RedoCommandTest {

    private final Model model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstPlace(model);
        deleteFirstPlace(model);
        model.undoTravelBuddy();
        model.undoTravelBuddy();

        deleteFirstPlace(expectedModel);
        deleteFirstPlace(expectedModel);
        expectedModel.undoTravelBuddy();
        expectedModel.undoTravelBuddy();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoTravelBuddy();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoTravelBuddy();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
