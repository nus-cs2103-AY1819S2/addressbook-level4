package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_INSIDE_TEST_SESSION;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and junit tests for {@code TestCommand}.
 */
public class TestCommandTest {
    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validTestCommand_success() {
        model.setActiveCardFolderIndex(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        TestCommand testCommand = new TestCommand();
        expectedModel.setActiveCardFolderIndex(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        expectedModel.testCardFolder();
        CommandResult expectedCommandResult = new CommandResult(TestCommand.MESSAGE_ENTER_TEST_FOLDER_SUCCESS,
                CommandResult.Type.START_TEST_SESSION);
        assertCommandSuccess(testCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidTestCommandNotInFolder_fail() {
        model.exitFoldersToHome();
        TestCommand testCommand = new TestCommand();
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER);
        assertCommandFailure(testCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidTestCommandInsideTestSession_fail() {
        TestCommand testCommand = new TestCommand();
        model.setActiveCardFolderIndex(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());
        model.testCardFolder();
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_INSIDE_TEST_SESSION);
        assertCommandFailure(testCommand, model, commandHistory, expectedMessage);
    }
}
