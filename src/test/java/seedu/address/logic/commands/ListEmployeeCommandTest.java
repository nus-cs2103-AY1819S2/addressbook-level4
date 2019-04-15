package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showEmployeeAtIndex;
import static seedu.address.testutil.TypicalEmployees.getTypicalPocketProjectWithEmployees;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EMPLOYEE;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListEmployeeCommand.
 */
public class ListEmployeeCommandTest {

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalPocketProjectWithEmployees(), new UserPrefs());
        expectedModel = new ModelManager(model.getPocketProject(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListEmployeeCommand(), model, commandHistory,
                ListEmployeeCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showEmployeeAtIndex(model, INDEX_FIRST_EMPLOYEE);
        assertCommandSuccess(new ListEmployeeCommand(), model, commandHistory, ListEmployeeCommand.MESSAGE_SUCCESS,
                expectedModel);
    }
}
