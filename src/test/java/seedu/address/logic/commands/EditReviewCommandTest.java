package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditReviewCommand.EditReviewDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PostalDataSet;
import seedu.address.model.UserPrefs;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.review.Review;
import seedu.address.testutil.EditReviewDescriptorBuilder;
import seedu.address.testutil.RestaurantBuilder;
import seedu.address.testutil.ReviewBuilder;

public class EditReviewCommandTest {
    private static final String DEFAULT_ENTRY = "Amazing place";
    private static final String DEFAULT_RATING = "4";
    private Model model = new ModelManager(getTypicalFoodDiary(), new UserPrefs(), new PostalDataSet());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_noRestaurantSelected_failure() {
        Restaurant firstRestaurantInModel = model.getFilteredRestaurantList().get(0);
        Review initialReview = new ReviewBuilder().build();
        Restaurant initialRestaurant = new RestaurantBuilder(firstRestaurantInModel).withReviews(List.of(initialReview))
                .build();
        model.setRestaurant(firstRestaurantInModel, initialRestaurant);

        String expectedMessage = String.format(Messages.MESSAGE_NO_RESTAURANT_SELECTED);

        EditReviewDescriptor editReviewDescriptor = new EditReviewDescriptorBuilder().withEntry(DEFAULT_ENTRY)
                .withRating(DEFAULT_RATING).build();
        EditReviewCommand editReviewCommand = new EditReviewCommand(Index.fromZeroBased(0), editReviewDescriptor);

        assertCommandFailure(editReviewCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_noReviews_failure() {
        Restaurant firstRestaurantInModel = model.getFilteredRestaurantList().get(0);
        model.setSelectedRestaurant(firstRestaurantInModel);

        String expectedMessage = String.format(Messages.MESSAGE_NO_REVIEWS);

        EditReviewDescriptor editReviewDescriptor = new EditReviewDescriptorBuilder().withEntry(DEFAULT_ENTRY)
                .withRating(DEFAULT_RATING).build();
        EditReviewCommand editReviewCommand = new EditReviewCommand(Index.fromZeroBased(0), editReviewDescriptor);

        assertCommandFailure(editReviewCommand, model, commandHistory, expectedMessage);
    }

    @Test
    public void execute_invalidIndex_failure() {
        Restaurant firstRestaurantInModel = model.getFilteredRestaurantList().get(0);
        Review initialReview = new ReviewBuilder().build();
        Restaurant initialRestaurant = new RestaurantBuilder(firstRestaurantInModel).withReviews(List.of(initialReview))
                .build();
        model.setRestaurant(firstRestaurantInModel, initialRestaurant);
        model.setSelectedRestaurant(initialRestaurant);

        EditReviewDescriptor editReviewDescriptor = new EditReviewDescriptorBuilder().withEntry(DEFAULT_ENTRY)
                .withRating(DEFAULT_RATING).build();
        EditReviewCommand editReviewCommand = new EditReviewCommand(Index.fromZeroBased(1), editReviewDescriptor);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_REVIEW_INDEX);

        assertCommandFailure(editReviewCommand, model, commandHistory, expectedMessage);
    }

}
