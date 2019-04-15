package seedu.hms.logic.commands;

import static seedu.hms.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.hms.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.Test;

import seedu.hms.logic.CommandHistory;
import seedu.hms.model.CustomerManager;
import seedu.hms.model.CustomerModel;
import seedu.hms.model.Model;

public class HelpCommandTest {
    private Model model = new CustomerManager();
    private CustomerModel expectedModel = new CustomerManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }
}
