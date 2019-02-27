package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPlaceAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIFTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.place.Place;

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
        Place placeToDeleteFirst = model.getFilteredPlaceList().get(INDEX_FIRST_PERSON.getZeroBased());
        Place placeToDeleteSecond = model.getFilteredPlaceList().get(INDEX_SECOND_PERSON.getZeroBased());
        Place placeToDeleteThird = model.getFilteredPlaceList().get(INDEX_THIRD_PERSON.getZeroBased());
        Place placeToDeleteFourth = model.getFilteredPlaceList().get(INDEX_FOURTH_PERSON.getZeroBased());

        expectedMessage = buildExpectedMessage(expectedMessage, placeToDeleteFirst);
        expectedMessage = buildExpectedMessage(expectedMessage, placeToDeleteSecond);
        expectedMessage = buildExpectedMessage(expectedMessage, placeToDeleteThird);
        expectedMessage = buildExpectedMessage(expectedMessage, placeToDeleteFourth);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePlace(placeToDeleteFirst);
        expectedModel.deletePlace(placeToDeleteSecond);
        expectedModel.deletePlace(placeToDeleteThird);
        expectedModel.deletePlace(placeToDeleteFourth);
        expectedModel.commitAddressBook();

        assertCommandSuccess(deleteMultipleCommand, model, commandHistory, expectedMessage.toString(), expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundStartIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);
        Index outOfBoundEndIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 3);
        DeleteMultipleCommand deleteMultipleCommand =
                new DeleteMultipleCommand(outOfBoundStartIndex, outOfBoundEndIndex);

        assertCommandFailure(deleteMultipleCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPlaceAtIndex(model, INDEX_FIRST_PERSON);

        Place placeToDelete = model.getFilteredPlaceList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteMultipleCommand deleteMultipleCommand = new DeleteMultipleCommand(INDEX_FIRST_PERSON, INDEX_FIFTH_PERSON);

        StringBuilder buildExpectedMessage = new StringBuilder();
        buildExpectedMessage.append(DeleteMultipleCommand.MESSAGE_DELETE_PERSON_SUCCESS);
        buildExpectedMessage.append(placeToDelete);
        buildExpectedMessage.append("\n");

        String expectedMessage = buildExpectedMessage.toString();

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePlace(placeToDelete);
        expectedModel.commitAddressBook();
        showNoPlace(expectedModel);

        assertCommandSuccess(deleteMultipleCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPlaceAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundStartIndex = INDEX_SECOND_PERSON;
        Index outOfBoundEndIndex = INDEX_FIFTH_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundStartIndex.getZeroBased() < model.getAddressBook().getPlaceList().size());
        assertTrue(outOfBoundEndIndex.getZeroBased() < model.getAddressBook().getPlaceList().size());

        DeleteMultipleCommand deleteMultipleCommand =
                new DeleteMultipleCommand(outOfBoundStartIndex, outOfBoundEndIndex);

        assertCommandFailure(deleteMultipleCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Place placeToDeleteFirst = model.getFilteredPlaceList().get(INDEX_FIRST_PERSON.getZeroBased());
        Place placeToDeleteSecond = model.getFilteredPlaceList().get(INDEX_SECOND_PERSON.getZeroBased());
        Place placeToDeleteThird = model.getFilteredPlaceList().get(INDEX_THIRD_PERSON.getZeroBased());

        DeleteMultipleCommand deleteMultipleCommand = new DeleteMultipleCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePlace(placeToDeleteFirst);
        expectedModel.deletePlace(placeToDeleteSecond);
        expectedModel.deletePlace(placeToDeleteThird);
        expectedModel.commitAddressBook();

        // deletem -> first 3 places deleted
        deleteMultipleCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered place list to show all places
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first 3 places deleted again
        expectedModel.redoAddressBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundStartIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);
        Index outOfBoundEndIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 3);
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
     * 1. Deletes a {@code Place} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted place in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the place object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_samePlaceDeleted() throws Exception {
        DeleteMultipleCommand deleteMultipleCommand = new DeleteMultipleCommand(INDEX_FIRST_PERSON, INDEX_THIRD_PERSON);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        showPlaceAtIndex(model, INDEX_SECOND_PERSON);
        Place placeToDelete = model.getFilteredPlaceList().get(INDEX_FIRST_PERSON.getZeroBased());
        expectedModel.deletePlace(placeToDelete);
        expectedModel.commitAddressBook();

        // delete -> deletes second place in unfiltered place list / first place in filtered place list
        deleteMultipleCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered place list to show all places
        expectedModel.undoAddressBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(placeToDelete, model.getFilteredPlaceList().get(INDEX_FIRST_PERSON.getZeroBased()));
        // redo -> deletes same second place in unfiltered place list
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

        // different place -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPlace(Model model) {
        model.updateFilteredPlaceList(p -> false);

        assertTrue(model.getFilteredPlaceList().isEmpty());
    }

    /**
     * Builds the expected message used for testing purposes
     */
    private StringBuilder buildExpectedMessage(StringBuilder expectedMessage, Place placeToDelete) {
        expectedMessage.append(DeleteMultipleCommand.MESSAGE_DELETE_PERSON_SUCCESS);
        expectedMessage.append(placeToDelete);
        expectedMessage.append("\n");
        return expectedMessage;
    }
}
