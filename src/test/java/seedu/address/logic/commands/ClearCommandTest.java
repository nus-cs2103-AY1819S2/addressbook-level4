package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEmployees.getTypicalPocketProjectWithEmployees;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PocketProject;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyPocketProject_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitPocketProject();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyPocketProject_success() {
        Model model = new ModelManager(getTypicalPocketProjectWithEmployees(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalPocketProjectWithEmployees(), new UserPrefs());
        expectedModel.setPocketProject(new PocketProject());
        expectedModel.commitPocketProject();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
