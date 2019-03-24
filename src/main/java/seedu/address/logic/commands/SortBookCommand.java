package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.book.BookListFilterPredicate;

/**
 * Sort all books in provided order.
 */
public class SortBookCommand extends Command {

    public static final String COMMAND_WORD = "sortBook";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": sort all books in certain order"
        + "the specified keywords (case-insensitive).\n"
        + "Parameters: [n/BOOKNAME]...[a/AUTHOR]...[t/TAGS]...[m/RATING]...\n"
        + "Example: " + COMMAND_WORD + " rating asc";

    public static final String MESSAGE_SUCCESS = "sorted successfully";

    private final BookListFilterPredicate predicate;

    public SortBookCommand(BookListFilterPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredBookList(predicate);
        return new CommandResult(
            String.format(Messages.MESSAGE_BOOKS_LISTED_OVERVIEW, model.getFilteredBookList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof SortBookCommand; // instanceof handles nulls

    }
}
