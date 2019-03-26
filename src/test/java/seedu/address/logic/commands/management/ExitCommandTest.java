package seedu.address.logic.commands.management;

import static seedu.address.logic.commands.management.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.management.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.ManagementModelManager;

/**
 * Unit tests for the {@link ExitCommand}.
 */
public class ExitCommandTest {
    private ManagementModel managementModel = new ManagementModelManager();
    private ManagementModel expectedManagementModel = new ManagementModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, false, true);
        assertCommandSuccess(new ExitCommand(), managementModel, commandHistory,
                expectedCommandResult, expectedManagementModel);
    }
}
