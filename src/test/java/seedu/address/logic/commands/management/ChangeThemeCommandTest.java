package seedu.address.logic.commands.management;

import static seedu.address.logic.commands.management.ChangeThemeCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.management.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.Model;
import seedu.address.model.modelmanager.QuizModelManager;
import seedu.address.testutil.Assert;

public class ChangeThemeCommandTest {
    private ManagementModel managementModel = new ManagementModelManager();
    private ManagementModel expectedManagementModel = new ManagementModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_wrongModel_throwsCommandException() {
        Model model = new QuizModelManager();
        Assert.assertThrows(CommandException.class, () ->
            new ChangeThemeCommand().execute(model, commandHistory));
    }

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_SUCCESS, "dark"));
        expectedManagementModel.changeTheme();
        assertCommandSuccess(new ChangeThemeCommand(), managementModel, commandHistory,
            expectedCommandResult, expectedManagementModel);
    }
}
