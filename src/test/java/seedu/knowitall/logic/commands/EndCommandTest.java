package seedu.knowitall.logic.commands;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.logic.commands.EndCommand.MESSAGE_END_TEST_SESSION_SUCCESS;
import static seedu.knowitall.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and junit tests for {@code EndCommand}.
 */
public class EndCommandTest {
    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_endTestSession_success() {
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        model.testCardFolder();
        expectedModel.testCardFolder();

        expectedModel.endTestSession();
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_END_TEST_SESSION_SUCCESS,
                CommandResult.Type.END_TEST_SESSION);
        assertCommandSuccess(new EndCommand(), model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_endTestSession_fail() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN);
        assertCommandFailure(new EndCommand(), model, commandHistory, expectedMessage);
    }
}
