package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;
import seedu.address.model.book.Review;

/**
 * Adds a review to the book.
 */
public class AddReviewCommand extends Command {

    public static final String COMMAND_WORD = "addReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a review to a certain book. \n "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_AUTHOR + "AUTHOR "
            + PREFIX_REVIEW + "REVIEW \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Alice in Wonderland "
            + PREFIX_AUTHOR + "Lewis Carroll "
            + PREFIX_REVIEW + "While Lewis Carroll purists will scoff at the aging of his young protagonist...";

    public static final String MESSAGE_SUCCESS = "New review added: %1$s";
    public static final String MESSAGE_BOOK_NOT_EXIST = "Add review failed.The book does not exist in the bookshelf.";

    private final Review toAdd;
    private final Book bookForReview;

    /**
     * Creates an AddReviewCommand to add the specified {@code Review}
     */
    public AddReviewCommand(Review review, Book book) {
        requireNonNull(review);
        toAdd = review;
        bookForReview = book;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (!model.hasBook(bookForReview)) {
            throw new CommandException(MESSAGE_BOOK_NOT_EXIST);
        }
        model.addReview(toAdd, bookForReview);
        model.commitBookShelf();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReviewCommand // instanceof handles nulls
                && toAdd.equals(((AddReviewCommand) other).toAdd)
                && bookForReview.equals(((AddReviewCommand) other).bookForReview));
    }
}
