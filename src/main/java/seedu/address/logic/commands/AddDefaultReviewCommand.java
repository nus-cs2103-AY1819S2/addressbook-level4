package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddDefaultReviewUtil.DEFAULT_ENTRIES;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.review.Review;

/**
 * Adds a default review to the specified restaurant in the food diary.
 */
public class AddDefaultReviewCommand extends Command {

    public static final String COMMAND_WORD = "addR";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a default review tagged to a restaurant to the "
            + "food diary.\n"
            + "Parameters: "
            + "INDEX (Must be a positive integer) "
            + "DEFAULT_REVIEW_NUMBER(integer from 1 - 5 inclusive)\n"
            + "Example: " + COMMAND_WORD + " "
            + "2 2\n"
            + "will add a review of (Rating: 2.0, Entry: Below average, try not to go again) to the restaurant"
            + " corresponding to index 2.";

    private final Index targetIndex;
    private final String defaultReviewIndicator;

    /**
     * Creates an AddCommand to add the specified {@code Restaurant}
     */
    public AddDefaultReviewCommand(Index targetIndex, String defaultReviewIndicator) {
        requireNonNull(defaultReviewIndicator);
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.defaultReviewIndicator = defaultReviewIndicator;
    }

    public String getDefaultReviewIndicator() {
        return defaultReviewIndicator;
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        int defaultReviewIndicatorInt = Integer.parseInt(defaultReviewIndicator);

        if (defaultReviewIndicatorInt > 5 || defaultReviewIndicatorInt < 1) {
            throw new CommandException(Messages.MESSAGE_INVALID_DEFAULT_REVIEW_INDEX);
        }

        Entry newEntry = new Entry(DEFAULT_ENTRIES.get(Integer.parseInt(defaultReviewIndicator) - 1));
        Rating newRating = new Rating(defaultReviewIndicator);
        Review reviewToAdd = new Review(newEntry, newRating);

        CommandResult result = new AddReviewCommand(targetIndex, reviewToAdd).execute(model, history);

        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDefaultReviewCommand // instanceof handles nulls
                && defaultReviewIndicator.equals(((AddDefaultReviewCommand) other).getDefaultReviewIndicator())
                && targetIndex.equals(((AddDefaultReviewCommand) other).getTargetIndex()));
    }

    @Override
    public String toString() {
        return this.getTargetIndex().getOneBased() + " " + this.getDefaultReviewIndicator();
    }
}
