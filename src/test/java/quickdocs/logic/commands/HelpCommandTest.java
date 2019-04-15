package quickdocs.logic.commands;

import static quickdocs.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(HelpCommand.SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }
}
