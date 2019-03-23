package seedu.address.logic.commands.request;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains unit tests for ListRequestCommand.
 */
public class ListRequestCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalHealthWorkerBook(),
            getTypicalRequestBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), getTypicalHealthWorkerBook(),
            model.getRequestBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListRequestCommand(), model, commandHistory,
            ListRequestCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        // TODO Hui Chun - integration test once the panel displays requests
        // showRequestAtIndex(model, INDEX_FIRST);
        // assertCommandSuccess(new ListRequestCommand, model, commandHistory, ListRequestCommand
        // .MESSAGE_SUCCESS, expectedModel);
    }
}
