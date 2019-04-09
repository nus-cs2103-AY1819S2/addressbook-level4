package quickdocs.logic.commands;

import org.junit.Test;

import quickdocs.logic.CommandHistory;
import quickdocs.model.Model;
import quickdocs.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT,
                false, true);
        CommandTestUtil.assertCommandSuccess(new ExitCommand(), model, commandHistory, expectedCommandResult,
                expectedModel);
    }
}
