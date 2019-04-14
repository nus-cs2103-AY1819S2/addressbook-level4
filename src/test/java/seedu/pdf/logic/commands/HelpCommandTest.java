package seedu.pdf.logic.commands;

import static seedu.pdf.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pdf.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Test;

import seedu.pdf.logic.CommandHistory;
import seedu.pdf.model.Model;
import seedu.pdf.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }
}
