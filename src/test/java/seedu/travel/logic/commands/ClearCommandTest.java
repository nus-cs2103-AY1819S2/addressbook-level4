package seedu.travel.logic.commands;

import static seedu.travel.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

import org.junit.Test;

import seedu.travel.logic.CommandHistory;
import seedu.travel.model.Model;
import seedu.travel.model.ModelManager;
import seedu.travel.model.TravelBuddy;
import seedu.travel.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyTravelBuddy_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitTravelBuddy();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyTravelBuddy_success() {
        Model model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
        expectedModel.setTravelBuddy(new TravelBuddy());
        expectedModel.commitTravelBuddy();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
