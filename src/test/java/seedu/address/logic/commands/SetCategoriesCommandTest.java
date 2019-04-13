package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CUISINE_FAST_FOOD;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OCCASION_CASUAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_RANGE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PostalDataSet;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.categories.Categories;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.restaurant.categories.Occasion;
import seedu.address.model.restaurant.categories.PriceRange;
import seedu.address.testutil.RestaurantBuilder;

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
}
