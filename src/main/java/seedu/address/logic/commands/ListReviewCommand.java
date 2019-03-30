package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

/**
 * Lists the review of the book.
 */
public class ListReviewCommand extends Command {
    public static final String COMMAND_WORD = "listReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists the reviews of the book identified by the index number used in the displayed book list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Listed reviews of book number: %1$s";

    private final Index targetIndex;

    public ListReviewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        List<Book> filteredBookList = model.getFilteredBookList();

        if (targetIndex.getZeroBased() >= filteredBookList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_BOOK_DISPLAYED_INDEX);
        }

        Book selectedBook = filteredBookList.get(targetIndex.getZeroBased());
        model.setSelectedBook(selectedBook);

        return new CommandResult(String.format(MESSAGE_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListReviewCommand // instanceof handles nulls
                && targetIndex.equals(((ListReviewCommand) other).targetIndex)); // state check
    }
}
