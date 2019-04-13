package seedu.knowitall.logic.commands;

import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.testutil.TypicalCards;

public class HelpCommandTest {
    private Model model = new ModelManager(TypicalCards.getTypicalFolderOneAsList());
    private Model expectedModel = new ModelManager(TypicalCards.getTypicalFolderOneAsList());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, CommandResult.Type.SHOW_HELP);
        assertCommandSuccess(new HelpCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }
}
