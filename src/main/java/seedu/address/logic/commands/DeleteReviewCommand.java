package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEWTITLE;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;

/**
 * Deletes the review of the specified book with the specified title.
 */
public class DeleteReviewCommand extends Command {

    public static final String COMMAND_WORD = "deleteReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the review of the specified book with the specified title. \n"
            + "Parameters: "
            + PREFIX_NAME + "BOOK NAME"
            + PREFIX_REVIEWTITLE + "REVIEW TITLE"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Alice in Wonderland"
            + PREFIX_REVIEWTITLE + "A great fairytale";

    public static final String MESSAGE_DELETE_REVIEW_SUCCESS = "Deleted Review: %1$s";

    private final BookNameContainsExactKeywordsPredicate bookPredicate;

    public DeleteReviewCommand(BookNameContainsExactKeywordsPredicate bookPredicate) {
        this.bookPredicate = bookPredicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredBookList(bookPredicate);
        List<Book> lastShownList = model.getFilteredBookList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_BOOK);
        }

        Book bookToDelete = lastShownList.get(0);
        model.deleteBook(bookToDelete);
        model.commitBookShelf();
        return new CommandResult(String.format(MESSAGE_DELETE_REVIEW_SUCCESS, bookToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteReviewCommand // instanceof handles nulls
                && bookPredicate.equals(((DeleteReviewCommand) other).bookPredicate)); // state check
    }
}
