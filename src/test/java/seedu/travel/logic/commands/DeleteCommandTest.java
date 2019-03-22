package seedu.travel.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.travel.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.travel.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.travel.logic.commands.CommandTestUtil.showPlaceAtIndex;
import static seedu.travel.testutil.TypicalIndexes.INDEX_FIRST_PLACE;
import static seedu.travel.testutil.TypicalIndexes.INDEX_SECOND_PLACE;
import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

import org.junit.Test;

import seedu.travel.commons.core.Messages;
import seedu.travel.commons.core.index.Index;
import seedu.travel.logic.CommandHistory;
import seedu.travel.model.Model;
import seedu.travel.model.ModelManager;
import seedu.travel.model.UserPrefs;
import seedu.travel.model.place.Place;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalTravelBuddy(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Place placeToDelete = model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PLACE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PLACE_SUCCESS, placeToDelete);

        ModelManager expectedModel = new ModelManager(model.getTravelBuddy(), new UserPrefs());
        expectedModel.deletePlace(placeToDelete);
        expectedModel.commitTravelBuddy();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPlaceAtIndex(model, INDEX_FIRST_PLACE);

        Place placeToDelete = model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PLACE);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PLACE_SUCCESS, placeToDelete);

        Model expectedModel = new ModelManager(model.getTravelBuddy(), new UserPrefs());
        expectedModel.deletePlace(placeToDelete);
        expectedModel.commitTravelBuddy();
        showNoPlace(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPlaceAtIndex(model, INDEX_FIRST_PLACE);

        Index outOfBoundIndex = INDEX_SECOND_PLACE;
        // ensures that outOfBoundIndex is still in bounds of travel book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTravelBuddy().getPlaceList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Place placeToDelete = model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PLACE);
        Model expectedModel = new ModelManager(model.getTravelBuddy(), new UserPrefs());
        expectedModel.deletePlace(placeToDelete);
        expectedModel.commitTravelBuddy();

        // delete -> first place deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts TravelBuddy back to previous state and filtered place list to show all places
        expectedModel.undoTravelBuddy();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first place deleted again
        expectedModel.redoTravelBuddy();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> travel book state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);

        // single travel book state in model -> undoCommand and redoCommand fail
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
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PLACE);
        Model expectedModel = new ModelManager(model.getTravelBuddy(), new UserPrefs());

        showPlaceAtIndex(model, INDEX_SECOND_PLACE);
        Place placeToDelete = model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased());
        expectedModel.deletePlace(placeToDelete);
        expectedModel.commitTravelBuddy();

        // delete -> deletes second place in unfiltered place list / first place in filtered place list
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts TravelBuddy back to previous state and filtered place list to show all places
        expectedModel.undoTravelBuddy();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(placeToDelete, model.getFilteredPlaceList().get(INDEX_FIRST_PLACE.getZeroBased()));
        // redo -> deletes same second place in unfiltered place list
        expectedModel.redoTravelBuddy();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PLACE);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PLACE);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_PLACE);
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
}
