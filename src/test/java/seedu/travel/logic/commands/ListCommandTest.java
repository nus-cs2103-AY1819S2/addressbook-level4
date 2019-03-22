package seedu.travel.logic.commands;

import static seedu.travel.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.travel.logic.commands.CommandTestUtil.showPlaceAtIndex;
import static seedu.travel.testutil.TypicalIndexes.INDEX_FIRST_PLACE;
import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

import org.junit.Before;
import org.junit.Test;

import seedu.travel.logic.CommandHistory;
import seedu.travel.model.Model;
import seedu.travel.model.ModelManager;
import seedu.travel.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
        expectedModel = new ModelManager(model.getTravelBuddy(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPlaceAtIndex(model, INDEX_FIRST_PLACE);
        assertCommandSuccess(new ListCommand(), model, commandHistory, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
