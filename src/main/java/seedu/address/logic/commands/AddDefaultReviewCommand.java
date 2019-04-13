package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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

    public static final String MESSAGE_SUCCESS = "New review added to: %1$s";

    private final Index targetIndex;
    private final Review defaultReviewToAdd;

    /**
     * Creates an AddCommand to add the specified {@code Restaurant}
     */
    public AddDefaultReviewCommand(Index targetIndex, Review defaultReviewToAdd) {
        requireNonNull(defaultReviewToAdd);
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.defaultReviewToAdd = defaultReviewToAdd;
    }

    public Review getDefaultReviewToAdd() {
        return defaultReviewToAdd;
    }

    public Index getTargetIndex() {
        return targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        CommandResult result = new AddReviewCommand(targetIndex, defaultReviewToAdd).execute(model, history);

        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddDefaultReviewCommand // instanceof handles nulls
                && defaultReviewToAdd.getEntry().equals(((AddDefaultReviewCommand) other).getDefaultReviewToAdd()
                .getEntry())
                && defaultReviewToAdd.getRating().equals(((AddDefaultReviewCommand) other).getDefaultReviewToAdd()
                .getRating())
                && targetIndex.equals(((AddDefaultReviewCommand) other).getTargetIndex()));
    }

    @Override
    public String toString() {
        return this.getTargetIndex().getOneBased() + " " + this.getDefaultReviewToAdd();
    }
}
