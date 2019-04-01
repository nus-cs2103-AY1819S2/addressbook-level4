package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListHealthWorkerCommand.
 */
public class ListHealthWorkerCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalHealthWorkerBook(),
                getTypicalRequestBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getHealthWorkerBook(),
            getTypicalRequestBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListHealthWorkerCommand(), model, commandHistory,
                ListHealthWorkerCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        // TODO: Hui Chun - Integration test once UI is implemented
        // showPersonAtIndex(model, INDEX_FIRST);
        // assertCommandSuccess(new ListHealthWorkerCommand(), model, commandHistory,
        //        ListHealthWorkerCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
