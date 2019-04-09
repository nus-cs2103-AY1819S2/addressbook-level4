package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TabCommand.MESSAGE_TAB_SUCCESS;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.CurrentEdit;
import seedu.address.model.CurrentEditManager;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class TabCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CurrentEdit currentEdit = new CurrentEditManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_tab_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_TAB_SUCCESS, false, false);
        assertCommandSuccess(new TabCommand(), model, commandHistory, expectedCommandResult, expectedModel,
            currentEdit);
    }
}
