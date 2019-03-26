package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEWTITLE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;
import seedu.address.model.book.Review;

/**
 * Deletes the review of the specified book with the specified title.
 */
public class DeleteReviewCommand extends Command {

    public static final String COMMAND_WORD = "deleteReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the review identified by the index number used in the displayed review list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_REVIEW_SUCCESS = "Deleted Review: %1$s";

    private final Index targetIndex;

    public DeleteReviewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Review> lastShownList = model.getFilteredReviewList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REVIEW_DISPLAYED_INDEX);
        }

        Review reviewToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteReview(reviewToDelete);
        model.commitBookShelf();
        return new CommandResult(String.format(MESSAGE_DELETE_REVIEW_SUCCESS, reviewToDelete.getTitle().fullName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReviewCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteReviewCommand) other).targetIndex)); // state check
    }
}
