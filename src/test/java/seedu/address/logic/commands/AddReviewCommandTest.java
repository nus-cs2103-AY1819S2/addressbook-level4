package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_RESTAURANT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_RESTAURANT;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import java.util.List;

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
import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.review.Review;
import seedu.address.testutil.RestaurantBuilder;
import seedu.address.testutil.ReviewBuilder;

public class AddReviewCommandTest {

    private Model model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_normalUnfilteredList_success() {
        Review reviewToAdd = new ReviewBuilder().build();
        Restaurant editedRestaurant = new RestaurantBuilder(model.getFilteredRestaurantList().get(0))
                .withReviews(List.of(reviewToAdd)).build();
        AddReviewCommand addReviewCommand = new AddReviewCommand(INDEX_FIRST_RESTAURANT, reviewToAdd);

        String expectedMessage = String.format(AddReviewCommand.MESSAGE_SUCCESS, editedRestaurant.getName());

        Model expectedModel = new ModelManager(new FoodDiary(model.getFoodDiary()),
                new UserPrefs(), new PostalDataSet());
        expectedModel.setRestaurant(model.getFilteredRestaurantList().get(0), editedRestaurant);
        expectedModel.setSelectedRestaurant(editedRestaurant);
        expectedModel.commitFoodDiary();

        assertCommandSuccess(addReviewCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Review reviewToAdd = new ReviewBuilder().build();
        int sizeOfList = model.getFilteredRestaurantList().size();
        Index outOfBoundsIndex = Index.fromOneBased(sizeOfList + 1);
        AddReviewCommand addReviewCommand = new AddReviewCommand(outOfBoundsIndex, reviewToAdd);

        assertCommandFailure(addReviewCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
    }

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
