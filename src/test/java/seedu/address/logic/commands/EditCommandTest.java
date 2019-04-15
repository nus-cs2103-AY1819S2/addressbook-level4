package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
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
import seedu.address.logic.commands.EditCommand.EditRestaurantDescriptor;
import seedu.address.model.FoodDiary;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PostalDataSet;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.testutil.EditRestaurantDescriptorBuilder;
import seedu.address.testutil.RestaurantBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Restaurant editedRestaurant = new RestaurantBuilder().build();
        EditRestaurantDescriptor descriptor = new EditRestaurantDescriptorBuilder(editedRestaurant).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESTAURANT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESTAURANT_SUCCESS, editedRestaurant);

        Model expectedModel = new ModelManager(new FoodDiary(model.getFoodDiary()),
                new UserPrefs(), new PostalDataSet());
        expectedModel.setRestaurant(model.getFilteredRestaurantList().get(0), editedRestaurant);
        expectedModel.commitFoodDiary();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastRestaurant = Index.fromOneBased(model.getFilteredRestaurantList().size());
        Restaurant lastRestaurant = model.getFilteredRestaurantList().get(indexLastRestaurant.getZeroBased());

        RestaurantBuilder restaurantInList = new RestaurantBuilder(lastRestaurant);
        Restaurant editedRestaurant = restaurantInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditRestaurantDescriptor descriptor = new EditRestaurantDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastRestaurant, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESTAURANT_SUCCESS, editedRestaurant);

        Model expectedModel = new ModelManager(new FoodDiary(model.getFoodDiary()),
                new UserPrefs(), new PostalDataSet());
        expectedModel.setRestaurant(lastRestaurant, editedRestaurant);
        expectedModel.commitFoodDiary();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESTAURANT, new EditRestaurantDescriptor());
        Restaurant editedRestaurant = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESTAURANT_SUCCESS, editedRestaurant);
        Model expectedModel = new ModelManager(new FoodDiary(model.getFoodDiary()),
                new UserPrefs(), new PostalDataSet());
        expectedModel.commitFoodDiary();
        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showRestaurantAtIndex(model, INDEX_FIRST_RESTAURANT);
        Restaurant restaurantInFilteredList = model.getFilteredRestaurantList()
                .get(INDEX_FIRST_RESTAURANT.getZeroBased());
        Restaurant editedRestaurant = new RestaurantBuilder(restaurantInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESTAURANT,
                new EditRestaurantDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_RESTAURANT_SUCCESS, editedRestaurant);

        Model expectedModel = new ModelManager(new FoodDiary(model.getFoodDiary()), new UserPrefs(),
                new PostalDataSet());
        expectedModel.setRestaurant(model.getFilteredRestaurantList().get(0), editedRestaurant);
        expectedModel.commitFoodDiary();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateRestaurantUnfilteredList_failure() {
        Restaurant firstRestaurant = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        EditRestaurantDescriptor descriptor = new EditRestaurantDescriptorBuilder(firstRestaurant).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_RESTAURANT, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_RESTAURANT);
    }

    @Test
    public void execute_duplicateRestaurantFilteredList_failure() {
        showRestaurantAtIndex(model, INDEX_FIRST_RESTAURANT);

        // edit restaurant in filtered list into a duplicate in food diary
        Restaurant restaurantInList = model.getFoodDiary().getRestaurantList()
                .get(INDEX_SECOND_RESTAURANT.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESTAURANT,
                new EditRestaurantDescriptorBuilder(restaurantInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_RESTAURANT);
    }

    @Test
    public void execute_invalidRestaurantIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRestaurantList().size() + 1);
        EditRestaurantDescriptor descriptor = new EditRestaurantDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of food diary
     */
    @Test
    public void execute_invalidRestaurantIndexFilteredList_failure() {
        showRestaurantAtIndex(model, INDEX_FIRST_RESTAURANT);
        Index outOfBoundIndex = INDEX_SECOND_RESTAURANT;
        // ensures that outOfBoundIndex is still in bounds of food diary list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFoodDiary().getRestaurantList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditRestaurantDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Restaurant editedRestaurant = new RestaurantBuilder().build();
        Restaurant restaurantToEdit = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        EditRestaurantDescriptor descriptor = new EditRestaurantDescriptorBuilder(editedRestaurant).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESTAURANT, descriptor);
        Model expectedModel = new ModelManager(new FoodDiary(model.getFoodDiary()), new UserPrefs(),
                new PostalDataSet());
        expectedModel.setRestaurant(restaurantToEdit, editedRestaurant);
        expectedModel.commitFoodDiary();

        // edit -> first restaurant edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts fooddiary back to previous state and filtered restaurant list to show all restaurants
        expectedModel.undoFoodDiary();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first restaurant edited again
        expectedModel.redoFoodDiary();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRestaurantList().size() + 1);
        EditRestaurantDescriptor descriptor = new EditRestaurantDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> food diary state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);

        // single food diary state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Restaurant} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited restaurant in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the restaurant object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameRestaurantEdited() throws Exception {
        Restaurant editedRestaurant = new RestaurantBuilder().build();
        EditRestaurantDescriptor descriptor = new EditRestaurantDescriptorBuilder(editedRestaurant).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_RESTAURANT, descriptor);
        Model expectedModel = new ModelManager(new FoodDiary(model.getFoodDiary()), new UserPrefs(),
                new PostalDataSet());

        showRestaurantAtIndex(model, INDEX_SECOND_RESTAURANT);
        Restaurant restaurantToEdit = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        expectedModel.setRestaurant(restaurantToEdit, editedRestaurant);
        expectedModel.commitFoodDiary();

        // edit -> edits second restaurant in unfiltered restaurant list / first restaurant in filtered restaurant list
        editCommand.execute(model, commandHistory);

        // undo -> reverts fooddiary back to previous state and filtered restaurant list to show all restaurants
        expectedModel.undoFoodDiary();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased()), restaurantToEdit);
        // redo -> edits same second restaurant in unfiltered restaurant list
        expectedModel.redoFoodDiary();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_RESTAURANT, DESC_AMY);

        // same values -> returns true
        EditRestaurantDescriptor copyDescriptor = new EditRestaurantDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_RESTAURANT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_RESTAURANT, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_RESTAURANT, DESC_BOB)));
    }

}
