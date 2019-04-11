package seedu.knowitall.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Test;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and junit tests for {@code ChangeDirectoryCommand}.
 */
public class ChangeDirectoryCommandTest {
    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validChangeCommandIntoFolder_success() {
        expectedModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD.getZeroBased());

        ChangeDirectoryCommand command = new ChangeDirectoryCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(
                        ChangeDirectoryCommand.MESSAGE_ENTER_FOLDER_SUCCESS,
                        TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getOneBased()),
                CommandResult.Type.ENTERED_FOLDER);
        assertCommandSuccess(command, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_validChangeCommandExitFolder_success() {
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());

        ChangeDirectoryCommand command = new ChangeDirectoryCommand();
        CommandResult expectedCommandResult = new CommandResult(ChangeDirectoryCommand.MESSAGE_EXIT_FOLDER_SUCCESS,
                CommandResult.Type.EXITED_FOLDER);
        assertCommandSuccess(command, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidEnterFolderCommand_failure() {
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());

        ChangeDirectoryCommand command = new ChangeDirectoryCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER);
        String expectedMessage = Messages.MESSAGE_INVALID_COMMAND_INSIDE_FOLDER;
        assertCommandFailure(command, model, commandHistory, expectedMessage);

        model.exitFolderToHome();

        command = new ChangeDirectoryCommand(TypicalIndexes.INDEX_SECOND_CARD_FOLDER);
        expectedMessage = Messages.MESSAGE_INVALID_FOLDER_DISPLAYED_INDEX;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidExitFolderCommand_failure() {
        model.exitFolderToHome();

        ChangeDirectoryCommand command = new ChangeDirectoryCommand();
        String expectedMessage = Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        ChangeDirectoryCommand exitFolderCommand = new ChangeDirectoryCommand();

        ChangeDirectoryCommand enterFolderOneCommand =
                new ChangeDirectoryCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER);
        ChangeDirectoryCommand enterFolderTwoCommand =
                new ChangeDirectoryCommand(TypicalIndexes.INDEX_SECOND_CARD_FOLDER);

        // same object -> returns true
        assertTrue(exitFolderCommand.equals(exitFolderCommand));

        // same values -> returns true
        assertTrue(exitFolderCommand.equals(new ChangeDirectoryCommand()));
        assertTrue(enterFolderOneCommand.equals(new ChangeDirectoryCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER)));

        // different types -> returns false
        assertFalse(exitFolderCommand.equals(enterFolderOneCommand));

        // different values -> returns false
        assertFalse(enterFolderOneCommand.equals(enterFolderTwoCommand));

        // null -> returns false
        assertFalse(enterFolderOneCommand.equals(null));
    }
}
