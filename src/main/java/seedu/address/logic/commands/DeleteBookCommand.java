package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REVIEWS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewBookNameContainsExactKeywordsPredicate;

/**
 * Deletes a book identified using it's displayed index from the book shelf.
 */
public class DeleteBookCommand extends Command {

    public static final String COMMAND_WORD = "deleteBook";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the book identified by the index number used in the displayed book list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_BOOK_SUCCESS = "Deleted Book: %1$s";

    private final Index targetIndex;

    public DeleteBookCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Book> lastShownList = model.getFilteredBookList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        Book bookToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteBook(bookToDelete);

        // check for reviews linked to this book and delete them
        ReviewBookNameContainsExactKeywordsPredicate predicate =
                new ReviewBookNameContainsExactKeywordsPredicate(bookToDelete.getBookName());
        model.updateFilteredReviewList(predicate);
        List<Review> reviewsToDelete = model.getFilteredReviewList();
        while (!reviewsToDelete.isEmpty()) {
            model.deleteReview(reviewsToDelete.get(0));
            model.updateFilteredReviewList(predicate);
            reviewsToDelete = model.getFilteredReviewList();
        }

        // show all reviews
        model.updateFilteredReviewList(PREDICATE_SHOW_ALL_REVIEWS);
        model.commitBookShelf();
        return new CommandResult(String.format(MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBookCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteBookCommand) other).targetIndex)); // state check
    }
}
