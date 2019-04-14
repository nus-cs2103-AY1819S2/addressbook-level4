package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CUISINE_FAST_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OCCASION_CASUAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_RANGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.FoodDiary;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PostalDataSet;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.categories.Categories;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.restaurant.categories.Occasion;
import seedu.address.model.restaurant.categories.PriceRange;
import seedu.address.model.review.Review;
import seedu.address.testutil.RestaurantBuilder;
import seedu.address.testutil.TypicalCategories;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * SetCategoriesCommand.
 */
public class SetCategoriesCommandTest {

    private Model model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validCategoriesUnfilteredList_success() {
        Restaurant restaurantCategoriesAdded = new RestaurantBuilder(model.getFilteredRestaurantList().get(0))
                .withCategories(new Cuisine(VALID_CUISINE_FAST_FOOD), new Occasion(VALID_OCCASION_CASUAL),
                        new PriceRange(VALID_PRICE_RANGE)).build();
        Categories validCategories = new Categories(new Cuisine(VALID_CUISINE_FAST_FOOD),
                new Occasion(VALID_OCCASION_CASUAL), new PriceRange(VALID_PRICE_RANGE));
        SetCategoriesCommand categoryCommand = new SetCategoriesCommand(INDEX_FIRST_RESTAURANT, validCategories);

        String expectedMessage = String.format(SetCategoriesCommand.MESSAGE_SET_CATEGORIES_SUCCESS,
                restaurantCategoriesAdded);

        Model expectedModel = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
        expectedModel.setRestaurant(model.getFilteredRestaurantList().get(0), restaurantCategoriesAdded);
        expectedModel.commitFoodDiary();

        assertCommandSuccess(categoryCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someCategoriesUnfilteredList_success() {
        Restaurant restaurantCategoriesAdded = new RestaurantBuilder(model.getFilteredRestaurantList().get(0))
                .withCategories(new Cuisine(VALID_CUISINE_FAST_FOOD), null, new PriceRange(VALID_PRICE_RANGE))
                .build();
        Categories someCategories = new Categories(new Cuisine(VALID_CUISINE_FAST_FOOD), null,
                new PriceRange(VALID_PRICE_RANGE));
        SetCategoriesCommand categoryCommand = new SetCategoriesCommand(INDEX_FIRST_RESTAURANT, someCategories);

        String expectedMessage = String.format(SetCategoriesCommand.MESSAGE_SET_CATEGORIES_SUCCESS,
                restaurantCategoriesAdded);

        Model expectedModel = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
        expectedModel.setRestaurant(model.getFilteredRestaurantList().get(0), restaurantCategoriesAdded);
        expectedModel.commitFoodDiary();

        assertCommandSuccess(categoryCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidRestaurantIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRestaurantList().size() + 1);
        Categories allSet = TypicalCategories.VALID_ALL_SET;
        SetCategoriesCommand categoriesCommand = new SetCategoriesCommand(outOfBoundIndex, allSet);

        assertCommandFailure(categoriesCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Restaurant restaurantToSet = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        Categories categoriesToSet = TypicalCategories.VALID_ALL_SET;
        Restaurant updatedRestaurant = new Restaurant(restaurantToSet, categoriesToSet);
        SetCategoriesCommand categoriesCommand = new SetCategoriesCommand(INDEX_FIRST_RESTAURANT, categoriesToSet);
        Model expectedModel = new ModelManager(new FoodDiary(model.getFoodDiary()), new UserPrefs(),
                new PostalDataSet());
        expectedModel.setRestaurant(restaurantToSet, updatedRestaurant);
        expectedModel.commitFoodDiary();

        // setCategories -> first restaurant's categories set
        categoriesCommand.execute(model, commandHistory);

        // undo -> reverts fooddiary back to previous state and filtered restaurant list to show all restaurants
        expectedModel.undoFoodDiary();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first restaurant set again
        expectedModel.redoFoodDiary();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_reviewsUnchanged() throws Exception {
        Restaurant beforeCategoriesSet = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        List<Review> reviewsBeforeCommand = beforeCategoriesSet.getReviews();
        SetCategoriesCommand categoriesCommand = new SetCategoriesCommand(INDEX_FIRST_RESTAURANT,
                TypicalCategories.VALID_ALL_SET);

        categoriesCommand.execute(model, commandHistory);

        Restaurant afterCategoriesSet = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());
        List<Review> reviewsAfterCommand = afterCategoriesSet.getReviews();
        Assert.assertEquals(reviewsBeforeCommand, reviewsAfterCommand);
    }
}
