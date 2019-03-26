package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.review.Review;

/**
 * Edits the details of an existing review tagged to a restaurant in the Food Diary.
 */
public class DeleteReviewCommand extends Command {
    public static final String COMMAND_WORD = "deleteReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the review identified "
            + "by the index number used in the displayed review list "
            + "of the selected restaurant first, i.e. user needs to first "
            + "select a restaurant from the list of restaurants.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_REVIEW_SUCCESS = "Deleted review number %1$s for restaurant: %2$s";
    public static final String MESSAGE_NOT_DELETED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RESTAURANT = "This review already exists for this restaurant.";

    private final Index index;

    /**
     * @param index of the restaurant in the filtered restaurant list to edit
     */
    public DeleteReviewCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Restaurant selectedRestaurant = model.getSelectedRestaurant();

        if (selectedRestaurant == null) {
            throw new CommandException(Messages.MESSAGE_NO_RESTAURANT_SELECTED);
        }

        Restaurant restaurantWithDeletedReview = createRestaurantWithDeletedReview(selectedRestaurant, index);

        model.setRestaurant(selectedRestaurant, restaurantWithDeletedReview);
        model.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
        model.commitFoodDiary();
        return new CommandResult(String.format(MESSAGE_DELETE_REVIEW_SUCCESS, index.getOneBased(),
                restaurantWithDeletedReview.getName()));
    }

    /**
     * Creates and returns a {@code Restaurant} with the details of {@code restaurantToEdit}
     * edited with {@code editRestaurantDescriptor}.
     */
    private static Restaurant createRestaurantWithDeletedReview(Restaurant restaurantSelected, Index index) {
        assert restaurantSelected != null;

        List<Review> editedReviews = new ArrayList<>();
        editedReviews.addAll(restaurantSelected.getReviews());
        editedReviews.remove(index.getZeroBased());

        return new Restaurant(restaurantSelected.getName(), restaurantSelected.getPhone(),
                restaurantSelected.getEmail(),
                restaurantSelected.getAddress(), restaurantSelected.getTags(),
                restaurantSelected.getWeblink(), restaurantSelected.getOpeningHours(),
                restaurantSelected.getCategories(), editedReviews);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteReviewCommand)) {
            return false;
        }

        // state check
        DeleteReviewCommand e = (DeleteReviewCommand) other;
        return index.equals(e.index);
    }
}
