package seedu.knowitall.logic.commands;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Before;
import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and junit tests for {@code ReportCommand}.
 */
public class ReportCommandTest {
    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
    }

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

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        assertCommandFailure(reportCommand, model, commandHistory, expectedMessage);
    }
}
