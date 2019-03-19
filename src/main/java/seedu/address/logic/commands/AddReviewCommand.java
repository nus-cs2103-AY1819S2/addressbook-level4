package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW_ENTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW_RATING;
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
 * Adds a restaurant to the food diary.
 */
public class AddReviewCommand extends Command {

    public static final String COMMAND_WORD = "addReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a review tagged to a restaurant to the food "
            + "diary.\n"
            + "Parameters: "
            + "INDEX (Must be a positive integer) "
            + PREFIX_REVIEW_ENTRY + "REVIEW "
            + PREFIX_REVIEW_RATING + "RATING \n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_REVIEW_ENTRY + "Good food but terrible service. "
            + PREFIX_REVIEW_RATING + "3 ";

    public static final String MESSAGE_SUCCESS = "New review added to: %1$s";
    public static final String MESSAGE_DUPLICATE_RESTAURANT = "This restaurant already exists in the food diary";

    private final Index targetIndex;
    private final Review reviewToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Restaurant}
     */
    public AddReviewCommand(Index targetIndex, Review review) {
        requireNonNull(review);
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.reviewToAdd = review;
    }

    public Review getReviewToAdd() {
        return reviewToAdd;
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Restaurant> lastShownList = model.getFilteredRestaurantList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_RESTAURANT_DISPLAYED_INDEX);
        }

        Restaurant restaurantReviewed = lastShownList.get(targetIndex.getZeroBased());
        Restaurant restaurantWithNewReview = createRestaurantWithNewReview(restaurantReviewed, reviewToAdd);

        model.setRestaurant(restaurantReviewed, restaurantWithNewReview);
        model.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
        model.commitFoodDiary();
        return new CommandResult(String.format(MESSAGE_SUCCESS, restaurantWithNewReview.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReviewCommand // instanceof handles nulls
                && reviewToAdd.equals(((AddReviewCommand) other).getReviewToAdd())
                && targetIndex.equals(((AddReviewCommand) other).getTargetIndex()));
    }

    /**
     * Creates a new Restaurant with the newly added specified {@code Review}
     */
    private static Restaurant createRestaurantWithNewReview(Restaurant restaurantReviewed, Review reviewToAdd) {
        assert restaurantReviewed != null;

        List<Review> newReviews = new ArrayList<>();
        newReviews.addAll(restaurantReviewed.getReviews());
        newReviews.add(reviewToAdd);

        return new Restaurant(restaurantReviewed.getName(), restaurantReviewed.getPhone(),
                restaurantReviewed.getEmail(),
                restaurantReviewed.getAddress(), restaurantReviewed.getTags(),
                restaurantReviewed.getWeblink(), restaurantReviewed.getOpeningHours(),
                restaurantReviewed.getCategories(), newReviews);
    }

    @Override
    public String toString() {
        return this.getTargetIndex().getOneBased() + " " + this.getReviewToAdd().toString();
    }
}
