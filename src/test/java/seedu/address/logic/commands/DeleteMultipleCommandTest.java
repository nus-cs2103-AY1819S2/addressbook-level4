package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
 * {@code DeleteMultipleCommand}.
 */
public class DeleteMultipleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        DeleteMultipleCommand deleteMultipleCommand =
                new DeleteMultipleCommand(INDEX_FIRST_PERSON, INDEX_FOURTH_PERSON);
        StringBuilder expectedMessage = new StringBuilder();
        Person personToDeleteFirst = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToDeleteSecond = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person personToDeleteThird = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        Person personToDeleteFourth = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());

        expectedMessage = buildExpectedMessage(expectedMessage, personToDeleteFirst);
        expectedMessage = buildExpectedMessage(expectedMessage, personToDeleteSecond);
        expectedMessage = buildExpectedMessage(expectedMessage, personToDeleteThird);
        expectedMessage = buildExpectedMessage(expectedMessage, personToDeleteFourth);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDeleteFirst);
        expectedModel.deletePerson(personToDeleteSecond);
        expectedModel.deletePerson(personToDeleteThird);
        expectedModel.deletePerson(personToDeleteFourth);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteMultipleCommand, model, commandHistory, expectedMessage.toString(), expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundStartIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index outOfBoundEndIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 3);
        DeleteMultipleCommand deleteMultipleCommand =
                new DeleteMultipleCommand(outOfBoundStartIndex, outOfBoundEndIndex);

        assertCommandFailure(deleteMultipleCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteMultipleCommand deleteMultipleCommand = new DeleteMultipleCommand(INDEX_FIRST_PERSON, INDEX_FIFTH_PERSON);

        StringBuilder buildExpectedMessage = new StringBuilder();
        buildExpectedMessage.append(DeleteMultipleCommand.MESSAGE_DELETE_PERSON_SUCCESS);
        buildExpectedMessage.append(personToDelete);
        buildExpectedMessage.append("\n");

        String expectedMessage = buildExpectedMessage.toString();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();
        showNoPerson(expectedModel);

        assertCommandSuccess(deleteMultipleCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundStartIndex = INDEX_SECOND_PERSON;
        Index outOfBoundEndIndex = INDEX_FIFTH_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundStartIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        assertTrue(outOfBoundEndIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteMultipleCommand deleteMultipleCommand =
                new DeleteMultipleCommand(outOfBoundStartIndex, outOfBoundEndIndex);

        assertCommandFailure(deleteMultipleCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Person personToDeleteFirst = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personToDeleteSecond = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person personToDeleteThird = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());

        DeleteMultipleCommand deleteMultipleCommand = new DeleteMultipleCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDeleteFirst);
        expectedModel.deletePerson(personToDeleteSecond);
        expectedModel.deletePerson(personToDeleteThird);
        expectedModel.commitAddressBook();

        // deletem -> first 3 persons deleted
        deleteMultipleCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first 3 persons deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundStartIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Index outOfBoundEndIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 3);
        DeleteMultipleCommand deleteMultipleCommand =
                new DeleteMultipleCommand(outOfBoundStartIndex, outOfBoundEndIndex);

        // execution failed -> address book state not added into model
        assertCommandFailure(deleteMultipleCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Person} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted person in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the person object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePersonDeleted() throws Exception {
        DeleteMultipleCommand deleteMultipleCommand = new DeleteMultipleCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPersonAtIndex(model, INDEX_SECOND_PERSON);
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.deletePerson(personToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second person in unfiltered person list / first person in filtered person list
        deleteMultipleCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered person list to show all persons
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(personToDelete, model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second person in unfiltered person list
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteMultipleCommand deleteFirstCommand = new DeleteMultipleCommand(INDEX_FIRST_PERSON, INDEX_FIFTH_PERSON);
        DeleteMultipleCommand deleteSecondCommand = new DeleteMultipleCommand(INDEX_SECOND_PERSON, INDEX_FOURTH_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteMultipleCommand deleteFirstCommandCopy =
                new DeleteMultipleCommand(INDEX_FIRST_PERSON, INDEX_FIFTH_PERSON);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    /**
     * Builds the expected message used for testing purposes
     */
    private StringBuilder buildExpectedMessage(StringBuilder expectedMessage, Person personToDelete) {
        expectedMessage.append(DeleteMultipleCommand.MESSAGE_DELETE_PERSON_SUCCESS);
        expectedMessage.append(personToDelete);
        expectedMessage.append("\n");
        return expectedMessage;
    }
}
