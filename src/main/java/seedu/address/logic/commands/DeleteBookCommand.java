package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;

/**
 * Deletes a book identified using it's displayed index from the book shelf.
 */
public class DeleteBookCommand extends Command {

    public static final String COMMAND_WORD = "deleteBook";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the book identified by the exact name.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Alice in Wonderland ";

    public static final String MESSAGE_DELETE_BOOK_SUCCESS = "Deleted Book: %1$s";

    private final BookNameContainsExactKeywordsPredicate predicate;

    public DeleteBookCommand(BookNameContainsExactKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.updateFilteredBookList(predicate);
        List<Book> lastShownList = model.getFilteredBookList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_BOOK);
        }

        Book bookToDelete = lastShownList.get(0);
        model.deleteBook(bookToDelete);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        model.commitBookShelf();
        return new CommandResult(String.format(MESSAGE_DELETE_BOOK_SUCCESS, bookToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteBookCommand // instanceof handles nulls
                && predicate.equals(((DeleteBookCommand) other).predicate)); // state check
    }
}
