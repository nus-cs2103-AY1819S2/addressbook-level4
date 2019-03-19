package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.book.BookListFilterPredicate;

/**
 * Lists all books with the tags and rating desired.
 */
public class ListBookCommand extends Command {

    public static final String COMMAND_WORD = "listBook";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": list all books whose tags and rating match"
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: [n/BOOKNAME]...[a/AUTHOR]...[t/TAGS]...[m/RATING]...\n"
            + "Example: " + COMMAND_WORD + " t/textbook m/5";

    public static final String MESSAGE_SUCCESS = "Listed all books satisfy the requirement";

    private final BookListFilterPredicate predicate;

    public ListBookCommand(BookListFilterPredicate predicate) {
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
                || (other instanceof ListBookCommand // instanceof handles nulls
                && predicate.equals(((ListBookCommand) other).predicate)); // state check
    }
}
