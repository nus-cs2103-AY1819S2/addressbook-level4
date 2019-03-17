package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

/**
 * Adds a book to the book shelf.
 */
public class AddBookCommand extends Command {

    public static final String COMMAND_WORD = "addBook";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a book to the bookShelf. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AUTHOR + "AUTHOR "
            + PREFIX_RATING + "RATING "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Alice in Wonderland "
            + PREFIX_AUTHOR + "Lewis Carroll "
            + PREFIX_RATING + "5 "
            + PREFIX_TAG + "fantasy "
            + PREFIX_TAG + "good";

    public static final String MESSAGE_SUCCESS = "New book added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book already exists in the bookShelf";

    private final Book toAdd;

    /**
     * Creates an AddBookCommand to add the specified {@code Book}
     */
    public AddBookCommand(Book book) {
        requireNonNull(book);
        toAdd = book;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasBook(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        }

        model.addBook(toAdd);
        model.commitBookShelf();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddBookCommand // instanceof handles nulls
                && toAdd.equals(((AddBookCommand) other).toAdd));
    }
}
