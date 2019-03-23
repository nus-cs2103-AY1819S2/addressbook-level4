package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BOOKS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.BookNameContainsExactKeywordsPredicate;
import seedu.address.model.book.Rating;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing book in the book shelf.
 */
public class EditBookCommand extends Command {

    public static final String COMMAND_WORD = "editBook";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the book identified "
            + "by the exact book name. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_AUTHOR + "AUTHOR] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_NAME + " Alice in Wonderland "
            + PREFIX_AUTHOR + "Jimmy "
            + PREFIX_RATING + "1";

    public static final String MESSAGE_EDIT_BOOK_SUCCESS = "Edited Book: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to editBook must be provided.";
    public static final String MESSAGE_DUPLICATE_BOOK = "This book already exists in the book shelf.";

    private final BookNameContainsExactKeywordsPredicate predicate;
    private final EditBookDescriptor editBookDescriptor;

    /**
     * @param editBookDescriptor details to editBook the book with
     */
    public EditBookCommand(BookNameContainsExactKeywordsPredicate predicate, EditBookDescriptor editBookDescriptor) {
        requireNonNull(editBookDescriptor);

        this.predicate = predicate;
        this.editBookDescriptor = new EditBookDescriptor(editBookDescriptor);
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
        Book editBookedBook = createEditedBook(bookToEdit, editBookDescriptor);

        if (!bookToEdit.isSameBook(editBookedBook) && model.hasBook(editBookedBook)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        }

        model.setBook(bookToEdit, editBookedBook);
        model.updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
        model.commitBookShelf();
        return new CommandResult(String.format(MESSAGE_EDIT_BOOK_SUCCESS, editBookedBook));
    }

    /**
     * Creates and returns a {@code Book} with the details of {@code bookToEdit}
     * editBooked with {@code editBookDescriptor}.
     */
    private static Book createEditedBook(Book bookToEdit, EditBookDescriptor editBookDescriptor) {
        assert bookToEdit != null;

        BookName updatedName = editBookDescriptor.getName().orElse(bookToEdit.getBookName());
        Author updatedAuthor = editBookDescriptor.getAuthor().orElse(bookToEdit.getAuthor());
        Rating updatedRating = editBookDescriptor.getRating().orElse(bookToEdit.getRating());
        Set<Tag> updatedTags = editBookDescriptor.getTags().orElse(bookToEdit.getTags());

        return new Book(updatedName, updatedAuthor, updatedRating, updatedTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditBookCommand)) {
            return false;
        }

        // state check
        EditBookCommand e = (EditBookCommand) other;
        return predicate.equals(e.predicate) && editBookDescriptor.equals(e.editBookDescriptor);
    }

    /**
     * Stores the details to editBook the book with. Each non-empty field value will replace the
     * corresponding field value of the book.
     */
    public static class EditBookDescriptor {
        private BookName name;
        private Author author;
        private Rating rating;
        private Set<Tag> tags;

        public EditBookDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditBookDescriptor(EditBookDescriptor toCopy) {
            setName(toCopy.name);
            setAuthor(toCopy.author);
            setRating(toCopy.rating);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is editBooked.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(author, rating, tags);
        }

        public void setName(BookName name) {
            this.name = name;
        }

        public Optional<BookName> getName() {
            return Optional.ofNullable(name);
        }

        public void setAuthor(Author author) {
            this.author = author;
        }

        public Optional<Author> getAuthor() {
            return Optional.ofNullable(author);
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Optional<Rating> getRating() {
            return Optional.ofNullable(rating);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditBookDescriptor)) {
                return false;
            }

            // state check
            EditBookDescriptor e = (EditBookDescriptor) other;

            return getName().equals(e.getName())
                    && getAuthor().equals(e.getAuthor())
                    && getRating().equals(e.getRating())
                    && getTags().equals(e.getTags());
        }
    }
}
