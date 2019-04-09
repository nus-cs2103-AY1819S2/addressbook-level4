package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddDefaultReviewUtil.DEFAULT_ENTRY_1;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.FoodDiary;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PostalDataSet;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.review.Review;
import seedu.address.testutil.RestaurantBuilder;
import seedu.address.testutil.ReviewBuilder;

public class AddDefaultReviewCommandTest {

    private Model model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allArgumentsPresent_success() {
        Review defaultReviewToAdd = new ReviewBuilder().withEntry(DEFAULT_ENTRY_1).withRating("1").build();
        Restaurant editedRestaurant = new RestaurantBuilder(model.getFilteredRestaurantList().get(0))
                .withReviews(List.of(defaultReviewToAdd)).build();
        AddDefaultReviewCommand addDefaultReviewCommand = new AddDefaultReviewCommand(INDEX_FIRST_RESTAURANT,
                defaultReviewToAdd);

        String expectedMessage = String.format(AddDefaultReviewCommand.MESSAGE_SUCCESS, editedRestaurant.getName());

        Model expectedModel = new ModelManager(new FoodDiary(model.getFoodDiary()),
                new UserPrefs(), new PostalDataSet());
        expectedModel.setRestaurant(model.getFilteredRestaurantList().get(0), editedRestaurant);
        expectedModel.setSelectedRestaurant(editedRestaurant);
        expectedModel.commitFoodDiary();

        assertCommandSuccess(addDefaultReviewCommand, model, commandHistory, expectedMessage, expectedModel);
    }
}
