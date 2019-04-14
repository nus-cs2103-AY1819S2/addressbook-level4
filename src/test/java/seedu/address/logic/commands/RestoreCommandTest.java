package seedu.address.logic.commands;

import org.junit.Before;
import org.junit.Test;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ParserMode;
import seedu.address.model.UserPrefs;
import seedu.address.model.source.Source;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showSourceAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SOURCE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_SOURCE;
import static seedu.address.testutil.TypicalSources.getTypicalDeletedSources;
import static seedu.address.testutil.TypicalSources.getTypicalSourceManager;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code RestoreCommand}.
 */
public class RestoreCommandTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalSourceManager(), new UserPrefs(), getTypicalDeletedSources(), 0);
        model.setParserMode(ParserMode.RECYCLE_BIN); //start in recycle bin mode
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Source toRestore = model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());
        RestoreCommand restoreCommand = new RestoreCommand(INDEX_FIRST_SOURCE);

        String expectedMessage = String.format(RestoreCommand.MESSAGE_RESTORE_SOURCE_SUCCESS, toRestore);

        ModelManager expectedModel = new ModelManager(model.getSourceManager(), new UserPrefs(),
                model.getDeletedSources());
        expectedModel.setParserMode(ParserMode.RECYCLE_BIN); //switch to recycle bin mode

        // add deleted source back to source manager list
        expectedModel.addSource(toRestore);
        expectedModel.commitSourceManager();

        // remove deleted source back from deleted sources list
        expectedModel.removeDeletedSource(toRestore);
        expectedModel.commitDeletedSources();

        assertCommandSuccess(restoreCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredSourceList().size() + 1);
        RestoreCommand restoreCommand = new RestoreCommand(outOfBoundIndex);

        assertCommandFailure(restoreCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);

        Source toRestore = model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());
        RestoreCommand restoreCommand = new RestoreCommand(INDEX_FIRST_SOURCE);

        String expectedMessage = String.format(RestoreCommand.MESSAGE_RESTORE_SOURCE_SUCCESS, toRestore);

        ModelManager expectedModel = new ModelManager(model.getSourceManager(), new UserPrefs(),
                model.getDeletedSources());
        expectedModel.setParserMode(ParserMode.RECYCLE_BIN); //switch to recycle bin mode

        // add deleted source back to source manager list
        expectedModel.addSource(toRestore);
        expectedModel.commitSourceManager();

        // remove deleted source back from deleted sources list
        expectedModel.removeDeletedSource(toRestore);
        expectedModel.commitDeletedSources();

        assertCommandSuccess(restoreCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showSourceAtIndex(model, INDEX_FIRST_SOURCE);

        Index outOfBoundIndex = INDEX_SECOND_SOURCE;
        // ensures that outOfBoundIndex is still in bounds of source manager list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSourceManager().getSourceList().size());

        RestoreCommand restoreCommand = new RestoreCommand(outOfBoundIndex);

        assertCommandFailure(restoreCommand, model, commandHistory, Messages.MESSAGE_INVALID_SOURCE_DISPLAYED_INDEX);
    }

    /**
     * If user tries to restore a source from the Recycle Bin that already exists in the Source Manager,
     * a command exception error will be thrown to prevent conflicts from happening.
     */
    @Test
    public void execute_sameSourceInSourceManager_throwsCommandException() {
        Source toRestore = model.getFilteredSourceList().get(INDEX_FIRST_SOURCE.getZeroBased());
        RestoreCommand restoreCommand = new RestoreCommand(INDEX_FIRST_SOURCE);

        // add same source to source manager model
        model.addSource(toRestore);

        assertCommandFailure(restoreCommand, model, commandHistory, RestoreCommand.MESSAGE_RESTORE_DUPLICATE_SOURCE);
    }

    @Test
    public void equals() {
        RestoreCommand restoreFirstCommand = new RestoreCommand(INDEX_FIRST_SOURCE);
        RestoreCommand restoreSecondCommand = new RestoreCommand(INDEX_SECOND_SOURCE);

        // same object -> returns true
        assertTrue(restoreFirstCommand.equals(restoreFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_SOURCE);
        assertTrue(restoreFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(restoreFirstCommand.equals(1));

        // null -> returns false
        assertFalse(restoreFirstCommand.equals(null));

        // different source -> returns false
        assertFalse(restoreFirstCommand.equals(restoreSecondCommand));
    }
}
