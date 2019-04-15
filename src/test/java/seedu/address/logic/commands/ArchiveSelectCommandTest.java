package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showArchivedPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_BUYER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_SELLER;
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

/**
 * Contains integration tests (interaction with the Model) for {@code ArchiveSelectCommand}.
 */
public class ArchiveSelectCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalArchiveBook(),
            getTypicalPinBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalArchiveBook(),
            getTypicalPinBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredArchivedPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_BUYER);
        assertExecutionSuccess(INDEX_FIRST_SELLER);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredArchivedPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showArchivedPersonAtIndex(model, INDEX_FIRST_BUYER);
        showArchivedPersonAtIndex(expectedModel, INDEX_FIRST_BUYER);

        assertExecutionSuccess(INDEX_FIRST_BUYER);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showArchivedPersonAtIndex(model, INDEX_FIRST_BUYER);
        showArchivedPersonAtIndex(expectedModel, INDEX_FIRST_BUYER);

        Index outOfBoundsIndex = INDEX_SECOND_BUYER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getArchiveBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ArchiveSelectCommand archiveSelectFirstCommand = new ArchiveSelectCommand(INDEX_FIRST_BUYER);
        ArchiveSelectCommand archiveSelectSecondCommand = new ArchiveSelectCommand(INDEX_SECOND_BUYER);

        // same object -> returns true
        assertTrue(archiveSelectFirstCommand.equals(archiveSelectFirstCommand));

        // same values -> returns true
        ArchiveSelectCommand archiveSelectFirstCommandCopy = new ArchiveSelectCommand(INDEX_FIRST_BUYER);
        assertTrue(archiveSelectFirstCommand.equals(archiveSelectFirstCommandCopy));

        // different types -> returns false
        assertFalse(archiveSelectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(archiveSelectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(archiveSelectFirstCommand.equals(archiveSelectSecondCommand));
    }

    /**
     * Executes a {@code ArchiveSelectCommand} with the given {@code index},
     * and checks that the model's selected archived person is set to the person at {@code index}
     * in the filtered archived person list.
     */
    private void assertExecutionSuccess(Index index) {
        ArchiveSelectCommand archiveSelectCommand = new ArchiveSelectCommand(index);
        String expectedMessage = String.format(ArchiveSelectCommand.MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased());
        expectedModel.setSelectedArchivedPerson(model.getFilteredArchivedPersonList().get(index.getZeroBased()));

        assertCommandSuccess(archiveSelectCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    /**
     * Executes a {@code ArchiveSelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ArchiveSelectCommand archiveSelectCommand = new ArchiveSelectCommand(index);
        assertCommandFailure(archiveSelectCommand, model, commandHistory, expectedMessage);
    }
}
