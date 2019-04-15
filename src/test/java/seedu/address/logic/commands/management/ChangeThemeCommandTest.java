package seedu.address.logic.commands.management;

import static org.junit.Assert.assertEquals;
import static seedu.address.model.UserPrefs.DARK_THEME;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.modelmanager.ManagementModel;
import seedu.address.model.modelmanager.ManagementModelManager;
import seedu.address.model.modelmanager.ManagementModelStub;
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
    public void execute_changeTheme_success() throws Exception {
        MgtModelStub modelStub = new MgtModelStub();

        CommandResult commandResult = new ChangeThemeCommand().execute(modelStub, commandHistory);

        assertEquals(String.format(ChangeThemeCommand.MESSAGE_SUCCESS, "dark"),
            commandResult.getFeedbackToUser());
    }

    /**
     * A ManagementModel stub which always accept changing theme to dark.
     */
    private class MgtModelStub extends ManagementModelStub {
        @Override
        public String changeTheme() {
            return DARK_THEME;
        }
    }
}
