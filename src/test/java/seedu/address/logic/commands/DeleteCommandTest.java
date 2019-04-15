package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showRestaurantAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RESTAURANT;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PostalDataSet;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Restaurant;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Restaurant restaurantToDelete = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RESTAURANT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RESTAURANT_SUCCESS, restaurantToDelete);

        ModelManager expectedModel = new ModelManager(model.getFoodDiary(), new UserPrefs(), new PostalDataSet());
        expectedModel.deleteRestaurant(restaurantToDelete);
        expectedModel.commitFoodDiary();

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRestaurantList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showRestaurantAtIndex(model, INDEX_FIRST_RESTAURANT);

        Restaurant restaurantToDelete = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RESTAURANT);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_RESTAURANT_SUCCESS, restaurantToDelete);

        Model expectedModel = new ModelManager(model.getFoodDiary(), new UserPrefs(), new PostalDataSet());
        expectedModel.deleteRestaurant(restaurantToDelete);
        expectedModel.commitFoodDiary();
        showNoRestaurant(expectedModel);

        assertCommandSuccess(deleteCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showRestaurantAtIndex(model, INDEX_FIRST_RESTAURANT);

        Index outOfBoundIndex = INDEX_SECOND_RESTAURANT;
        // ensures that outOfBoundIndex is still in bounds of food diary list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFoodDiary().getRestaurantList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Restaurant restaurantToDelete = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RESTAURANT);
        Model expectedModel = new ModelManager(model.getFoodDiary(), new UserPrefs(), new PostalDataSet());
        expectedModel.deleteRestaurant(restaurantToDelete);
        expectedModel.commitFoodDiary();

        // delete -> first restaurant deleted
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts fooddiary back to previous state and filtered restaurant list to show all restaurants
        expectedModel.undoFoodDiary();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first restaurant deleted again
        expectedModel.redoFoodDiary();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRestaurantList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        // execution failed -> food diary state not added into model
        assertCommandFailure(deleteCommand, model, commandHistory, Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);

        // single food diary state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Deletes a {@code Restaurant} from a filtered list.
     * 2. Undo the deletion.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously deleted restaurant in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the deletion. This ensures {@code RedoCommand} deletes the restaurant object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameRestaurantDeleted() throws Exception {
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_RESTAURANT);
        Model expectedModel = new ModelManager(model.getFoodDiary(), new UserPrefs(), new PostalDataSet());

        showRestaurantAtIndex(model, INDEX_SECOND_RESTAURANT);
        Restaurant restaurantToDelete = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        expectedModel.deleteRestaurant(restaurantToDelete);
        expectedModel.commitFoodDiary();

        /* delete -> deletes second restaurant in unfiltered restaurant list / first restaurant in filtered
         * restaurant list
         */
        deleteCommand.execute(model, commandHistory);

        // undo -> reverts fooddiary back to previous state and filtered restaurant list to show all restaurants
        expectedModel.undoFoodDiary();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(restaurantToDelete, model.getFilteredRestaurantList()
                .get(INDEX_FIRST_RESTAURANT.getZeroBased()));
        // redo -> deletes same second restaurant in unfiltered restaurant list
        expectedModel.redoFoodDiary();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_RESTAURANT);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_RESTAURANT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(INDEX_FIRST_RESTAURANT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different restaurant -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoRestaurant(Model model) {
        model.updateFilteredRestaurantList(p -> false);

        assertTrue(model.getFilteredRestaurantList().isEmpty());
    }
}
