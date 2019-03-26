package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FULLSCREEN;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.EndCommand.MESSAGE_END_TEST_SESSION_SUCCESS;
import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and junit tests for {@code EndCommand}.
 */
public class EndCommandTest {
    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_endTestSession_success() {
        model.testCardFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.testCardFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());

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
