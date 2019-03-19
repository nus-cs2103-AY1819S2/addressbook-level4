package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEWENTRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEWRATING;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.review.Review;

/**
 * Edits the details of an existing review tagged to a restaurant in the Food Diary.
 */
public class EditReviewCommand extends Command {
    public static final String COMMAND_WORD = "editReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the review identified "
            + "by the index number used in the displayed review list "
            + "of the selected restaurant first, i.e. user needs to first "
            + "select a restaurant from the list of restaurants."
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_REVIEWENTRY + "ENTRY] "
            + "[" + PREFIX_REVIEWRATING + "RATING]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_REVIEWENTRY + "Poor food.";

    public static final String MESSAGE_EDIT_REVIEW_SUCCESS = "Edited review for restaurant: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_RESTAURANT = "This review already exists for this restaurant.";

    private final Index index;
    private final EditReviewDescriptor editReviewDescriptor;

    /**
     * @param index of the restaurant in the filtered restaurant list to edit
     * @param editReviewDescriptor details to edit the restaurant with
     */
    public EditReviewCommand(Index index, EditReviewDescriptor editReviewDescriptor) {
        requireNonNull(index);
        requireNonNull(editReviewDescriptor);

        this.index = index;
        this.editReviewDescriptor = editReviewDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Restaurant selectedRestaurant = model.getSelectedRestaurant();

        if (selectedRestaurant == null) {
            throw new CommandException(Messages.MESSAGE_NO_RESTAURANT_SELECTED);
        }

        Restaurant restaurantWithEditedReview = createRestaurantWithEditedReview(selectedRestaurant,
                editReviewDescriptor, index);

        model.setRestaurant(selectedRestaurant, restaurantWithEditedReview);
        model.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);
        model.commitFoodDiary();
        return new CommandResult(String.format(MESSAGE_EDIT_REVIEW_SUCCESS, restaurantWithEditedReview.getName()));
    }

    /**
     * Creates and returns a {@code Restaurant} with the details of {@code restaurantToEdit}
     * edited with {@code editRestaurantDescriptor}.
     */
    private static Restaurant createRestaurantWithEditedReview(Restaurant restaurantSelected, EditReviewDescriptor
                                                                editReviewDescriptor, Index index) {
        assert restaurantSelected != null;

        Entry updatedEntry = editReviewDescriptor.getEntry().orElse(restaurantSelected.getReviews()
                .get(index.getZeroBased()).getEntry());
        Rating updatedRating = editReviewDescriptor.getRating().orElse(restaurantSelected.getReviews()
                .get(index.getZeroBased()).getRating());

        Review editedReview = new Review(updatedEntry, updatedRating);
        List<Review> editedReviews = new ArrayList<>();
        editedReviews.addAll(restaurantSelected.getReviews());
        editedReviews.remove(index.getZeroBased());
        editedReviews.add(index.getZeroBased(), editedReview);

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
        if (!(other instanceof EditReviewCommand)) {
            return false;
        }

        // state check
        EditReviewCommand e = (EditReviewCommand) other;
        return index.equals(e.index)
                && editReviewDescriptor.equals(e.editReviewDescriptor);
    }

    /**
     * Stores the details to edit the restaurant with. Each non-empty field value will replace the
     * corresponding field value of the restaurant.
     */
    public static class EditReviewDescriptor {
        private Entry entry;
        private Rating rating;

        public EditReviewDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditReviewDescriptor(EditReviewCommand.EditReviewDescriptor toCopy) {
            setEntry(toCopy.entry);
            setRating(toCopy.rating);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(entry, rating);
        }

        public void setEntry(Entry entry) {
            this.entry = entry;
        }

        public Optional<Entry> getEntry() {
            return Optional.ofNullable(entry);
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Optional<Rating> getRating() {
            return Optional.ofNullable(rating);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditReviewCommand.EditReviewDescriptor)) {
                return false;
            }

            // state check
            EditReviewCommand.EditReviewDescriptor e = (EditReviewCommand.EditReviewDescriptor) other;

            return getEntry().equals(e.getEntry())
                    && getRating().equals(e.getRating());
        }
    }
}
