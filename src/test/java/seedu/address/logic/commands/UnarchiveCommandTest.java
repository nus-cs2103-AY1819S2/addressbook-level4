package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showArchivedPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUYER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_BUYER;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalArchiveBook;
import static seedu.address.testutil.TypicalPersons.getTypicalPinBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code UnarchiveCommand}.
 */
public class UnarchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchiveBook(),
            getTypicalPinBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToUnarchive = model.getFilteredArchivedPersonList().get(INDEX_FIRST_BUYER.getZeroBased());
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_BUYER);

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS, personToUnarchive);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getArchiveBook(),
                model.getPinBook(), new UserPrefs());
        expectedModel.unarchivePerson(personToUnarchive);
        expectedModel.commitBooks();
        expectedModel.setSelectedArchivedPerson(null);

        assertCommandSuccess(unarchiveCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredArchivedPersonList().size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(unarchiveCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showArchivedPersonAtIndex(model, INDEX_FIRST_BUYER);

        Person personToUnarchive = model.getFilteredArchivedPersonList().get(INDEX_FIRST_BUYER.getZeroBased());
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_BUYER);

        String expectedMessage = String.format(UnarchiveCommand.MESSAGE_UNARCHIVE_PERSON_SUCCESS, personToUnarchive);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getArchiveBook(),
                model.getPinBook(), new UserPrefs());
        expectedModel.unarchivePerson(personToUnarchive);
        expectedModel.commitBooks();
        expectedModel.setSelectedArchivedPerson(null);
        showNoPerson(expectedModel);

        assertCommandSuccess(unarchiveCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showArchivedPersonAtIndex(model, INDEX_FIRST_BUYER);

        Index outOfBoundIndex = INDEX_SECOND_BUYER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getArchiveBook().getPersonList().size());

        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        assertCommandFailure(unarchiveCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personToUnarchive = model.getFilteredArchivedPersonList().get(INDEX_FIRST_BUYER.getZeroBased());
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_BUYER);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getArchiveBook(),
                model.getPinBook(), new UserPrefs());
        expectedModel.unarchivePerson(personToUnarchive);
        expectedModel.commitBooks();
        expectedModel.setSelectedArchivedPerson(null);

        // unarchive -> first person unarchived
        unarchiveCommand.execute(model, commandHistory);

        // undo -> reverts books back to previous state
        expectedModel.undoBooks();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person unarchived again
        expectedModel.redoBooks();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredArchivedPersonList().size() + 1);
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(unarchiveCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Unarchives a {@code Person} from a filtered list.
     * 2. Undo the archiving.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously unarchived person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the archiving. This ensures {@code RedoCommand} unarchives the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonUnarchived() throws Exception {
        UnarchiveCommand unarchiveCommand = new UnarchiveCommand(INDEX_FIRST_BUYER);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getArchiveBook(),
                model.getPinBook(), new UserPrefs());

        showArchivedPersonAtIndex(model, INDEX_SECOND_BUYER);
        Person personToUnarchive = model.getFilteredArchivedPersonList().get(INDEX_FIRST_BUYER.getZeroBased());
        expectedModel.unarchivePerson(personToUnarchive);
        expectedModel.commitBooks();
        expectedModel.setSelectedArchivedPerson(null);

        // unarchive ->
        // unarchives second person in unfiltered archived person list / first person in filtered archived person list
        unarchiveCommand.execute(model, commandHistory);

        // undo -> reverts books back to previous state
        expectedModel.undoBooks();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(personToUnarchive, model.getFilteredArchivedPersonList().get(INDEX_FIRST_BUYER.getZeroBased()));
        // redo -> unarchives same second person in unfiltered person list
        expectedModel.redoBooks();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        UnarchiveCommand unarchiveFirstCommand = new UnarchiveCommand(INDEX_FIRST_BUYER);
        UnarchiveCommand unarchiveSecondCommand = new UnarchiveCommand(INDEX_SECOND_BUYER);

        // same object -> returns true
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommand));

        // same values -> returns true
        UnarchiveCommand unarchiveFirstCommandCopy = new UnarchiveCommand(INDEX_FIRST_BUYER);
        assertTrue(unarchiveFirstCommand.equals(unarchiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(unarchiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unarchiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(unarchiveFirstCommand.equals(unarchiveSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered archived list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredArchivedPersonList(p -> false);

        assertTrue(model.getFilteredArchivedPersonList().isEmpty());
    }

}
