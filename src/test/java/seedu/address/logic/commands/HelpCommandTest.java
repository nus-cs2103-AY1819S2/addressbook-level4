package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.management.HelpCommand.MESSAGE_SUCCESS;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.management.HelpCommand;
import seedu.address.model.modelmanager.management.ManagementModel;
import seedu.address.model.modelmanager.management.ManagementModelManager;

public class HelpCommandTest {
    private ManagementModel managementModel = new ManagementModelManager();
    private ManagementModel expectedManagementModel = new ManagementModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, true, false);
        assertCommandSuccess(new HelpCommand(), managementModel, commandHistory,
                expectedCommandResult, expectedManagementModel);
    }
}
