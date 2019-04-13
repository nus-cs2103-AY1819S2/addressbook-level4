package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showActivityAtIndex;
import static seedu.address.testutil.TypicalActivities.getTypicalAddressBookWithActivities;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ActivityListCommand.
 */
public class ActivityListCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookWithActivities(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ActivityListCommand(), model, commandHistory, ActivityListCommand.MESSAGE_SUCCESS,
                expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showActivityAtIndex(model, INDEX_FIRST_ACTIVITY);
        assertCommandSuccess(new ActivityListCommand(), model, commandHistory, ActivityListCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
