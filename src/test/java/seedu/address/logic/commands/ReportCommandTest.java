package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_INSIDE_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and junit tests for {@code ReportCommand}.
 */
public class ReportCommandTest {
    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validReportCommand_success() {
        ReportCommand reportCommand = new ReportCommand();
        expectedModel.enterReportDisplay();
        CommandResult expectedCommandResult = new CommandResult(ReportCommand.MESSAGE_SUCCESS,
                CommandResult.Type.ENTERED_REPORT);
        assertCommandSuccess(reportCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidReportCommandInsideReportSession_fail() {
        ReportCommand reportCommand = new ReportCommand();
        expectedModel.enterReportDisplay();
        CommandResult expectedCommandResult = new CommandResult(ReportCommand.MESSAGE_SUCCESS,
                CommandResult.Type.ENTERED_REPORT);
        assertCommandSuccess(reportCommand, model, commandHistory, expectedCommandResult, expectedModel);

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_INSIDE_REPORT);
        assertCommandFailure(reportCommand, model, commandHistory, expectedMessage);
    }
}
