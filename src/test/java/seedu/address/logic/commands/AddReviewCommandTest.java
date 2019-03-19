package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RESTAURANT;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PostalDataSet;
import seedu.address.model.UserPrefs;
import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.review.Review;

public class AddReviewCommandTest {

    private Model model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
    private CommandHistory commandHistory = new CommandHistory();

    /*@Test
    public void execute_normalUnfilteredList_success() {
        Review newReview = new ReviewBuilder().build();
        Restaurant firstRestaurant = model.getFilteredRestaurantList().get(INDEX_FIRST_RESTAURANT.getZeroBased());

        RestaurantBuilder rb = new RestaurantBuilder(firstRestaurant).withReview(newReview);
        //TODO: problem is that .withReview(newReview) somehow tries to modify unmodifiable list, returned by
        //getReviews() from restaurant class
        Restaurant editedRestaurant = new RestaurantBuilder(firstRestaurant).withReview(newReview).build();
        AddReviewCommand addReviewCommand = new AddReviewCommand(INDEX_FIRST_RESTAURANT, newReview);

        String expectedMessage = String.format(AddReviewCommand.MESSAGE_SUCCESS, editedRestaurant);

        Model expectedModel = new ModelManager(new FoodDiary(model.getFoodDiary()), new UserPrefs());
        expectedModel.setRestaurant(model.getFilteredRestaurantList().get(0), editedRestaurant);
        expectedModel.commitFoodDiary();

        assertCommandSuccess(addReviewCommand, model, commandHistory, expectedMessage, expectedModel);
    }*/

    @Test
    public void equals() {
        final Review standardReview = new Review(new Entry("Standard restaurant."), new Rating("3"));
        final AddReviewCommand standardCommand = new AddReviewCommand(INDEX_FIRST_RESTAURANT, standardReview);

        // same values -> returns true
        Review reviewWithSameValues = new Review(new Entry("Standard restaurant."), new Rating("3"),
                standardReview.getTimeStamp());
        AddReviewCommand commandWithSameValues = new AddReviewCommand(INDEX_FIRST_RESTAURANT, reviewWithSameValues);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddReviewCommand(INDEX_SECOND_RESTAURANT, standardReview)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AddReviewCommand(INDEX_FIRST_RESTAURANT,
                new Review(new Entry("Not standard Restaurant"), new Rating("3")))));
    }

}
