package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PostalDataSet;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.testutil.RestaurantBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
    }

    @Test
    public void execute_newRestaurant_success() {
        Restaurant validRestaurant = new RestaurantBuilder().build();

        Model expectedModel = new ModelManager(model.getFoodDiary(), new UserPrefs(), new PostalDataSet());
        expectedModel.addRestaurant(validRestaurant);
        expectedModel.commitFoodDiary();

        assertCommandSuccess(new AddCommand(validRestaurant), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS, validRestaurant), expectedModel);
    }

    @Test
    public void execute_duplicateRestaurant_throwsCommandException() {
        Restaurant restaurantInList = model.getFoodDiary().getRestaurantList().get(0);
        assertCommandFailure(new AddCommand(restaurantInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_RESTAURANT);
    }

}
