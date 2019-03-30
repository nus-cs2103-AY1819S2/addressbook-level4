package seedu.knowitall.logic.commands;

import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_INSIDE_TEST_SESSION;
import static seedu.knowitall.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Test;

import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and junit tests for {@code TestCommand}.
 */
public class TestCommandTest {
    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validTestCommand_success() {
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        TestCommand testCommand = new TestCommand();
        expectedModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.testCardFolder();
        CommandResult expectedCommandResult = new CommandResult(TestCommand.MESSAGE_ENTER_TEST_FOLDER_SUCCESS,
                CommandResult.Type.START_TEST_SESSION);
        assertCommandSuccess(testCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidTestCommandNotInFolder_fail() {
        model.exitFolderToHome();
        TestCommand testCommand = new TestCommand();
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        assertCommandFailure(testCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidTestCommandInsideTestSession_fail() {
        TestCommand testCommand = new TestCommand();
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        model.testCardFolder();
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_INSIDE_TEST_SESSION);
        assertCommandFailure(testCommand, model, commandHistory, expectedMessage);
    }
}
