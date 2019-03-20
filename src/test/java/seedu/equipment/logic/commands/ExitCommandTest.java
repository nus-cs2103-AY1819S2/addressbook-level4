package seedu.equipment.logic.commands;

import static seedu.equipment.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.equipment.logic.commands.ExitCommand.MESSAGE_EXIT_ACKNOWLEDGEMENT;

import org.junit.Test;

import seedu.equipment.logic.CommandHistory;
import seedu.equipment.logic.commands.CommandResult;
import seedu.equipment.logic.commands.ExitCommand;
import seedu.equipment.model.Model;
import seedu.equipment.model.ModelManager;

public class ExitCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_exit_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
        assertCommandSuccess(new ExitCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }
}
