package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalCardFolders;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalIndexes;

/**
 * Contains integration tests (interaction with the Model) and junit tests for {@code ChangeCommand}.
 */
public class ChangeCommandTest {
    private Model model = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalCardFolders(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validChangeCommandIntoFolder_success() {
        expectedModel.exitFoldersToHome();
        expectedModel.enterFolder(TypicalIndexes.INDEX_FIRST_CARD.getZeroBased());

        model.exitFoldersToHome();
        ChangeCommand changeCommand = new ChangeCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER);
        CommandResult expectedCommandResult = new CommandResult(
                String.format(
                        ChangeCommand.MESSAGE_ENTER_FOLDER_SUCCESS,
                        TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getOneBased()),
                CommandResult.Type.ENTERED_FOLDER);
        assertCommandSuccess(changeCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_validChangeCommandExitFolder_success() {
        expectedModel.exitFoldersToHome();

        ChangeCommand changeCommand = new ChangeCommand();
        CommandResult expectedCommandResult = new CommandResult(ChangeCommand.MESSAGE_EXIT_FOLDER_SUCCESS,
                CommandResult.Type.EXITED_FOLDER);
        assertCommandSuccess(changeCommand, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidEnterFolderCommand_failure() {
        ChangeCommand changeCommand = new ChangeCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER);
        String expectedMessage = Messages.MESSAGE_INVALID_COMMAND_INSIDE_FOLDER;
        assertCommandFailure(changeCommand, model, commandHistory, expectedMessage);

        model.exitFoldersToHome();

        changeCommand = new ChangeCommand(TypicalIndexes.INDEX_SECOND_CARD_FOLDER);
        expectedMessage = Messages.MESSAGE_INVALID_FOLDER_DISPLAYED_INDEX;
        assertCommandFailure(changeCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidExitFolderCommand_failure() {
        model.exitFoldersToHome();

        ChangeCommand changeCommand = new ChangeCommand();
        String expectedMessage = Messages.MESSAGE_INVALID_COMMAND_OUTSIDE_FOLDER;
        assertCommandFailure(changeCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        ChangeCommand changeExitFolderCommand = new ChangeCommand();

        ChangeCommand changeEnterFolderOneCommand = new ChangeCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER);
        ChangeCommand changeEnterFolderTwoCommand = new ChangeCommand(TypicalIndexes.INDEX_SECOND_CARD_FOLDER);

        // same object -> returns true
        assertTrue(changeExitFolderCommand.equals(changeExitFolderCommand));

        // same values -> returns true
        assertTrue(changeExitFolderCommand.equals(new ChangeCommand()));
        assertTrue(changeEnterFolderOneCommand.equals(new ChangeCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER)));

        // different types -> returns false
        assertFalse(changeExitFolderCommand.equals(changeEnterFolderOneCommand));

        // different values -> returns false
        assertFalse(changeEnterFolderOneCommand.equals(changeEnterFolderTwoCommand));

        // null -> returns false
        assertFalse(changeEnterFolderOneCommand.equals(null));
    }
}
