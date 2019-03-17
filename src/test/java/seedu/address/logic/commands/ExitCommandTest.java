package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.managementcommand.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.managementcommand.ExitCommand;
import seedu.address.model.modelmanager.managementmodel.ManagementModel;
import seedu.address.model.modelmanager.managementmodel.ManagementModelManager;

public class ExitCommandTest {
    private ManagementModel managementModel = new ManagementModelManager();
    private ManagementModel expectedManagementModel = new ManagementModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), managementModel, commandHistory, expectedCommandResult, expectedManagementModel);
    }
}
