package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPatients.getTypicalDocX;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DocX;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyDocX_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitDocX();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyDocX_success() {
        Model model = new ModelManager(getTypicalDocX(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalDocX(), new UserPrefs());
        expectedModel.setDocX(new DocX());
        expectedModel.commitDocX();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
