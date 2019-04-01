package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Review;

/**
 * Selects a review
 */
public class SelectReviewCommand extends Command {
    public static final String COMMAND_WORD = "selectReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the review identified by the index number used in the displayed book list.\n"
            + "The selected review message will be shown in the browser panel in the right.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Selected review: %1$s";

    private final Index targetIndex;

    public SelectReviewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Review> filteredReviewList = model.getFilteredReviewList();

        if (targetIndex.getZeroBased() >= filteredReviewList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REVIEW_DISPLAYED_INDEX);
        }

        Review selectedReview = filteredReviewList.get(targetIndex.getZeroBased());
        model.setSelectedReview(selectedReview);

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectReviewCommand // instanceof handles nulls
                && targetIndex.equals(((SelectReviewCommand) other).targetIndex)); // state check
    }
}
