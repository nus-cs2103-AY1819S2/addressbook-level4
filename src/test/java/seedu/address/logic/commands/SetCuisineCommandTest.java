package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CUISINE_FAST_FOOD;
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
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.testutil.RestaurantBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for
 * SetCuisineCommand.
 */
public class SetCuisineCommandTest {

    private Model model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validCuisineUnfilteredList_success() {
        Restaurant restaurantCuisineAdded = new RestaurantBuilder(model.getFilteredRestaurantList().get(0))
                .withCuisine(VALID_CUISINE_FAST_FOOD).build();
        Cuisine validCuisine = new Cuisine(VALID_CUISINE_FAST_FOOD);
        SetCuisineCommand cuisineCommand = new SetCuisineCommand(INDEX_FIRST_RESTAURANT, validCuisine);

        String expectedMessage = String.format(SetCuisineCommand.MESSAGE_SET_CUISINE_SUCCESS, restaurantCuisineAdded);

        Model expectedModel = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
        expectedModel.setRestaurant(model.getFilteredRestaurantList().get(0), restaurantCuisineAdded);
        expectedModel.commitFoodDiary();

        assertCommandSuccess(cuisineCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
