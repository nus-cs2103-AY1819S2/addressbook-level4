package seedu.knowitall.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.knowitall.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.knowitall.logic.commands.EditFolderCommand.MESSAGE_DUPLICATE_FOLDER;
import static seedu.knowitall.logic.commands.EditFolderCommand.MESSAGE_EDIT_FOLDER_SUCCESS;
import static seedu.knowitall.testutil.TypicalCards.getTypicalFolderOneAsList;

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

public class EditFolderCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();
    private Model model = new ModelManager(getTypicalFolderOneAsList(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFolderOneAsList(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EditFolderCommand(null, "");
    }

    @Test
    public void constructor_nullFolderName_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EditFolderCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER, null);
    }

    @Test
    public void execute_validEditCommandDifferentName_success() {
        String newName = TypicalCards.getTypicalFolderTwoName();

        expectedModel.renameFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased(), newName);

        EditFolderCommand command = new EditFolderCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER, newName);
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_EDIT_FOLDER_SUCCESS, newName),
                CommandResult.Type.EDITED_FOLDER);
        assertCommandSuccess(command, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_validEditCommandSameName_success() {
        String newName = TypicalCards.getTypicalFolderOneName();

        expectedModel.renameFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased(), newName);

        EditFolderCommand command = new EditFolderCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER, newName);
        CommandResult expectedCommandResult = new CommandResult(String.format(MESSAGE_EDIT_FOLDER_SUCCESS, newName),
                CommandResult.Type.EDITED_FOLDER);
        assertCommandSuccess(command, model, commandHistory, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidState_failure() {
        model.enterFolder(TypicalIndexes.INDEX_FIRST_CARD_FOLDER.getZeroBased());

        String newName = TypicalCards.getTypicalFolderTwoName();
        EditFolderCommand command = new EditFolderCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER, newName);
        String expectedMessage = Messages.MESSAGE_INVALID_COMMAND_INSIDE_FOLDER;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_failure() {
        String newName = TypicalCards.getTypicalFolderTwoName();
        EditFolderCommand command = new EditFolderCommand(TypicalIndexes.INDEX_SECOND_CARD_FOLDER, newName);
        String expectedMessage = Messages.MESSAGE_INVALID_FOLDER_DISPLAYED_INDEX;
        assertCommandFailure(command, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_duplicateFolder_failure() {
        model.addFolder(TypicalCards.getTypicalFolderTwo());

        String newName = TypicalCards.getTypicalFolderTwoName();
        EditFolderCommand command = new EditFolderCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER, newName);
        assertCommandFailure(command, model, commandHistory, MESSAGE_DUPLICATE_FOLDER);
    }

    @Test
    public void equals() {
        EditFolderCommand editCommandOneOne =
                new EditFolderCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER, TypicalCards.getTypicalFolderOneName());
        EditFolderCommand editCommandOneTwo =
                new EditFolderCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER, TypicalCards.getTypicalFolderTwoName());
        EditFolderCommand editCommandTwoTwo =
                new EditFolderCommand(TypicalIndexes.INDEX_SECOND_CARD_FOLDER, TypicalCards.getTypicalFolderTwoName());

        // same object -> returns true
        assertTrue(editCommandOneOne.equals(editCommandOneOne));

        // same values -> returns true
        assertTrue(editCommandOneOne.equals(
                new EditFolderCommand(TypicalIndexes.INDEX_FIRST_CARD_FOLDER, TypicalCards.getTypicalFolderOneName())));

        // different values -> returns false
        assertFalse(editCommandOneOne.equals(editCommandOneTwo)); // different indices
        assertFalse(editCommandOneTwo.equals(editCommandTwoTwo)); // different names
        assertFalse(editCommandOneOne.equals(editCommandTwoTwo)); // different indices and names

        // null -> returns false
        assertFalse(editCommandOneOne.equals(null));
    }
}
