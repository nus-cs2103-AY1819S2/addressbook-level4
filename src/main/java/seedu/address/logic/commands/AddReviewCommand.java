package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REVIEWTITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import java.util.List;
import java.util.Set;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Review;
import seedu.address.model.tag.Tag;

/**
 * Adds a review to the book.
 */
public class AddReviewCommand extends Command {

    public static final String COMMAND_WORD = "addReview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a review to a certain book. \n "
            + "Parameters: "
            + PREFIX_NAME + "BOOK NAME "
            + PREFIX_REVIEWTITLE + "REVIEW TITLE "
            + PREFIX_REVIEW + "REVIEW \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Alice in Wonderland "
            + PREFIX_REVIEWTITLE + "A great fairytale "
            + PREFIX_REVIEW + "While Lewis Carroll purists will scoff at the aging of his young protagonist...";

    public static final String MESSAGE_SUCCESS = "New review added: %1$s";

    private final BookNameContainsExactKeywordsPredicate predicate;
    private final Review toAdd;

    /**
     * Creates an AddReviewCommand to add the specified {@code Review}
     */
    public AddReviewCommand(Review review, BookNameContainsExactKeywordsPredicate predicate) {
        requireNonNull(review);
        requireNonNull(predicate);
        toAdd = review;
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

        Book bookToEdit = lastShownList.get(0);
        Book addedReview = createdAddedReview(bookToEdit, toAdd);

        model.setBook(bookToEdit, addedReview);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        model.commitBookShelf();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.title.fullName));

    }

    /**
     * Creates and returns a {@code Book} with added review {@code toAdd}
     */
    private static Book createdAddedReview(Book bookToEdit, Review toAdd) {
        requireNonNull(bookToEdit);
        requireNonNull(toAdd);

        BookName updatedName = bookToEdit.getBookName();
        Author updatedAuthor = bookToEdit.getAuthor();
        Rating updatedRating = bookToEdit.getRating();
        Set<Tag> updatedTags = bookToEdit.getTags();
        Set<Review> updatedReviews = bookToEdit.getReviews();
        updatedReviews.add(toAdd);

        return new Book(updatedName, updatedAuthor, updatedRating, updatedTags, updatedReviews);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddReviewCommand // instanceof handles nulls
                && toAdd.equals(((AddReviewCommand) other).toAdd)
                && predicate.equals(((AddReviewCommand) other).predicate));
    }

    @Override
    public String toString() {
        return ("Add: " + toAdd.toString() + " to: " + predicate.toString());
    }
}
