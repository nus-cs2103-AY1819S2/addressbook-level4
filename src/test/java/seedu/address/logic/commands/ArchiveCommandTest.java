package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
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
 * {@code ArchiveCommand}.
 */
public class ArchiveCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchiveBook(),
            getTypicalPinBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_BUYER.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_BUYER);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS, personToArchive);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getArchiveBook(),
                model.getPinBook(), new UserPrefs());
        expectedModel.archivePerson(personToArchive);
        expectedModel.commitBooks();
        expectedModel.setSelectedPerson(null);

        assertCommandSuccess(archiveCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_BUYER);

        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_BUYER.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_BUYER);

        String expectedMessage = String.format(ArchiveCommand.MESSAGE_ARCHIVE_PERSON_SUCCESS, personToArchive);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getArchiveBook(),
                model.getPinBook(), new UserPrefs());
        expectedModel.archivePerson(personToArchive);
        expectedModel.commitBooks();
        expectedModel.setSelectedPerson(null);
        showNoPerson(expectedModel);

        assertCommandSuccess(archiveCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_BUYER);

        Index outOfBoundIndex = INDEX_SECOND_BUYER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        assertCommandFailure(archiveCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_BUYER.getZeroBased());
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_BUYER);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getArchiveBook(),
                model.getPinBook(), new UserPrefs());
        expectedModel.archivePerson(personToArchive);
        expectedModel.commitBooks();
        expectedModel.setSelectedPerson(null);

        // archive -> first person archived
        archiveCommand.execute(model, commandHistory);

        // undo -> reverts books back to previous state
        expectedModel.undoBooks();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first person archived again
        expectedModel.redoBooks();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        ArchiveCommand archiveCommand = new ArchiveCommand(outOfBoundIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(archiveCommand, model, commandHistory, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Archives a {@code Person} from a filtered list.
     * 2. Undo the archiving.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously archived person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the archiving. This ensures {@code RedoCommand} archives the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonArchived() throws Exception {
        ArchiveCommand archiveCommand = new ArchiveCommand(INDEX_FIRST_BUYER);
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getArchiveBook(),
                model.getPinBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_BUYER);
        Person personToArchive = model.getFilteredPersonList().get(INDEX_FIRST_BUYER.getZeroBased());
        expectedModel.archivePerson(personToArchive);
        expectedModel.commitBooks();
        expectedModel.setSelectedPerson(null);

        // archive -> archives second person in unfiltered person list / first person in filtered person list
        archiveCommand.execute(model, commandHistory);

        // undo -> reverts books back to previous state
        expectedModel.undoBooks();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(personToArchive, model.getFilteredPersonList().get(INDEX_FIRST_BUYER.getZeroBased()));
        // redo -> archives same second person in unfiltered person list
        expectedModel.redoBooks();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ArchiveCommand archiveFirstCommand = new ArchiveCommand(INDEX_FIRST_BUYER);
        ArchiveCommand archiveSecondCommand = new ArchiveCommand(INDEX_SECOND_BUYER);

        // same object -> returns true
        assertTrue(archiveFirstCommand.equals(archiveFirstCommand));

        // same values -> returns true
        ArchiveCommand archiveFirstCommandCopy = new ArchiveCommand(INDEX_FIRST_BUYER);
        assertTrue(archiveFirstCommand.equals(archiveFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(archiveFirstCommand.equals(archiveSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

}
