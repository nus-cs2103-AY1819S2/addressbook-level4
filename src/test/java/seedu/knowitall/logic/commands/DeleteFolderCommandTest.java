package seedu.knowitall.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.logic.commands.DeleteFolderCommand.MESSAGE_DELETE_FOLDER_SUCCESS;
import static seedu.knowitall.testutil.TypicalCards.getTypicalFolderOneAsList;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.knowitall.commons.core.Messages;
import seedu.knowitall.logic.CommandHistory;
import seedu.knowitall.model.Model;
import seedu.knowitall.model.ModelManager;
import seedu.knowitall.model.UserPrefs;
import seedu.knowitall.testutil.TypicalCards;
import seedu.knowitall.testutil.TypicalIndexes;

public class DeleteFolderCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalFolderOneAsList(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new DeleteFolderCommand(null);
    }

    @Test
    public void execute_validDeleteCommand_success() {
        Model expectedModel = new ModelManager(new ArrayList<>(), new UserPrefs());;

        DeleteFolderCommand command = new DeleteFolderCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER);
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_DELETE_FOLDER_SUCCESS,
                TypicalCards.getTypicalFolderOne()));
        assertCommandSuccess(command, model, commandHistory, expectedCommandResult, expectedModel);
    }


    @Test
    public void execute_invalidState_failure() {
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());

        DeleteFolderCommand command = new DeleteFolderCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER);
        String expectedMessage = Messages.MESSAGE_INVALID_COMMAND_INSIDE_FOLDER;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_failure() {
        DeleteFolderCommand command = new DeleteFolderCommand(TypicalIndexes.INDEX_SECOND_CARD_FOLDER);
        String expectedMessage = Messages.MESSAGE_INVALID_FOLDER_DISPLAYED_INDEX;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {
        DeleteFolderCommand deleteCommandOne =
                new DeleteFolderCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER);
        DeleteFolderCommand deleteCommandTwo =
                new DeleteFolderCommand(TypicalIndexes.INDEX_SECOND_CARD_FOLDER);

        // same object -> returns true
        assertTrue(deleteCommandOne.equals(deleteCommandOne));

        // same values -> returns true
        assertTrue(deleteCommandOne.equals(new DeleteFolderCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER)));

        // different values -> returns false
        assertFalse(deleteCommandOne.equals(deleteCommandTwo));

        // null -> returns false
        assertFalse(deleteCommandOne.equals(null));
    }
}
