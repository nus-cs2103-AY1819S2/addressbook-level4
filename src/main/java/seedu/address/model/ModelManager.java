package seedu.address.model;

import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.book.Book;
import seedu.address.model.book.Review;
import seedu.address.model.book.exceptions.BookNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the book shelf data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedBookShelf versionedBookShelf;
    private final UserPrefs userPrefs;
    private final FilteredList<Book> filteredBooks;
    private final FilteredList<Review> filteredReviews;
    private final SimpleObjectProperty<Book> selectedBook = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<Review> selectedReview = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given book shelf and userPrefs.
     */
    public ModelManager(ReadOnlyBookShelf bookShelf, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(bookShelf, userPrefs);

        logger.fine("Initializing with book shelf: " + bookShelf + " and user prefs " + userPrefs);

        versionedBookShelf = new VersionedBookShelf(bookShelf);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredBooks = new FilteredList<>(versionedBookShelf.getBookList());
        filteredReviews = new FilteredList<>(versionedBookShelf.getReviewList());
        filteredBooks.addListener(this::ensureSelectedBookIsValid);
        filteredReviews.addListener(this::ensureSelectedReviewIsValid);
    }

    public ModelManager() {
        this(new BookShelf(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getBookShelfFilePath() {
        return userPrefs.getBookShelfFilePath();
    }

    @Override
    public void setBookShelfFilePath(Path bookShelfFilePath) {
        requireNonNull(bookShelfFilePath);
        userPrefs.setBookShelfFilePath(bookShelfFilePath);
    }

    //=========== BookShelf ================================================================================

    @Override
    public void setBookShelf(ReadOnlyBookShelf bookShelf) {
        versionedBookShelf.resetData(bookShelf);
    }

    @Override
    public ReadOnlyBookShelf getBookShelf() {
        return versionedBookShelf;
    }

    @Override
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return versionedBookShelf.hasBook(book);
    }

    @Override
    public boolean hasReview(Review review) {
        requireNonNull(review);
        return versionedBookShelf.hasReview(review);
    }

    public void deleteBook(Book target) {
        versionedBookShelf.removeBook(target);
    }

    public void deleteReview(Review target) {
        versionedBookShelf.removeReview(target);
    }

    @Override
    public void addBook(Book book) {
        versionedBookShelf.addBook(book);
        updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
    }

    @Override
    public void addReview(Review review) {
        versionedBookShelf.addReview(review);
        updateFilteredReviewList(PREDICATE_SHOW_ALL_REVIEWS);
    }

    @Override
    public void setBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);
        versionedBookShelf.setBook(target, editedBook);
    }

    @Override
    public void setReview(Review target, Review editedReview) {
        requireAllNonNull(target, editedReview);
        versionedBookShelf.setReview(target, editedReview);
    }

    @Override
    public void deleteTag(Tag tag) {
        versionedBookShelf.removeTag(tag);
    }

    @Override
    public void sortBook(List<String> types, String mainOrder, Map<String, String> subOrder) {
        versionedBookShelf.sort(types, mainOrder, subOrder);
    }

    //=========== Filtered Book List Accessors =============================================================

    @Override
    public ObservableList<Book> getFilteredBookList() {
        return filteredBooks;
    }

    @Override
    public ObservableList<Review> getFilteredReviewList() {
        return filteredReviews;
    }

    @Override
    public void updateFilteredBookList(Predicate<Book> predicate) {
        requireNonNull(predicate);
        filteredBooks.setPredicate(predicate);
    }

    @Override
    public void updateFilteredReviewList(Predicate<Review> predicate) {
        requireNonNull(predicate);
        filteredReviews.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoBookShelf() {
        return versionedBookShelf.canUndo();
    }

    @Override
    public boolean canRedoBookShelf() {
        return versionedBookShelf.canRedo();
    }

    @Override
    public void undoBookShelf() {
        versionedBookShelf.undo();
    }

    @Override
    public void redoBookShelf() {
        versionedBookShelf.redo();
    }

    @Override
    public void commitBookShelf() {
        versionedBookShelf.commit();
    }

    //=========== Selected book ===========================================================================

    @Override
    public ReadOnlyProperty<Book> selectedBookProperty() {
        return selectedBook;
    }

    @Override
    public ReadOnlyProperty<Review> selectedReviewProperty() {
        return selectedReview;
    }

    @Override
    public Book getSelectedBook() {
        return selectedBook.getValue();
    }

    @Override
    public Review getSelectedReview() {
        return selectedReview.getValue();
    }

    @Override
    public void setSelectedBook(Book book) {
        if (book != null && !filteredBooks.contains(book)) {
            throw new BookNotFoundException();
        }
        selectedBook.setValue(book);
    }

    @Override
    public void setSelectedReview(Review review) {
        if (review != null && !filteredReviews.contains(review)) {
            throw new BookNotFoundException();
        }
        selectedReview.setValue(review);
    }

    //=========== Summary ===========================================================================
    @Override
    public int getNumberOfBooks() {
        return versionedBookShelf.getBookList().size();
    }

    @Override
    public List<String> getMostReadAuthors() {
        Map<String, Integer> authorCounts = new HashMap<>();
        List<String> authorNames = new ArrayList<>();
        List<Book> bookList = versionedBookShelf.getBookList();
        //count books written by each author
        for (Book book : bookList) {
            String authorName = book.getAuthor().fullName;
            Integer authorCount = authorCounts.get(authorName);
            if (authorCount == null) {
                authorCounts.put(authorName, 1);
            } else {
                authorCounts.put(authorName, authorCount + 1);
            }
        }

        //return books written by authors appear more than once.
        for (String authorName: authorCounts.keySet()) {
            if (authorCounts.get(authorName) > 1) {
                authorNames.add(authorName);
            }
        }

        return authorNames;
    }

    public List<String> getBooksByAuthor(String authorName) {
        List<String> bookNames = new ArrayList<>();
        List<Book> booksInShelf = versionedBookShelf.getBookList();
        for (Book book : booksInShelf) {
            if (authorName.equalsIgnoreCase(book.getAuthor().fullName)) {
                bookNames.add(book.getBookName().fullName);
            }
        }
        return bookNames;
    }

    public String getHighestMark() {
        int highestMark = -1;
        List<Book> booksInShelf = versionedBookShelf.getBookList();
        for (Book book : booksInShelf) {
            int rating = parseInt(book.getRating().value);
            highestMark = rating > highestMark ? rating : highestMark;
        }
        return highestMark == -1 ? null : Integer.toString(highestMark);
    }

    public List<String> getBooksWithHighestMark(String mark) {
        List<String> bookNames = new ArrayList<>();
        List<Book> booksInShelf = versionedBookShelf.getBookList();
        for (Book book : booksInShelf) {
            if (book.getRating().value.equals(mark)) {
                bookNames.add(book.getBookName().fullName);
            }
        }
        return bookNames;
    }

    public List<String> getMostReadTags() {
        Map<String, Integer> tagCounts = new HashMap<>();
        List<String> tagContents = new ArrayList<>();
        List<Book> bookList = versionedBookShelf.getBookList();
        //count books written by each author
        for (Book book : bookList) {
            Set<Tag> bookTags = book.getTags();
            for (Tag tag: bookTags) {
                String tagContent = tag.tagName;
                Integer tagCount = tagCounts.get(tagContent);
                if (tagCount == null) {
                    tagCounts.put(tagContent, 1);
                } else {
                    tagCounts.put(tagContent, tagCount + 1);
                }
            }
        }

        //return books written by authors appear more than once.
        for (String tagContent: tagCounts.keySet()) {
            if (tagCounts.get(tagContent) > 1) {
                tagContents.add(tagContent);
            }
        }

        return tagContents;
    }

    public List<String> getBooksWithTag(String tagContent) {
        List<String> bookNames = new ArrayList<>();
        List<Book> booksInShelf = versionedBookShelf.getBookList();
        for (Book book : booksInShelf) {
            boolean containTag = book.getTags().stream().map(x -> x.tagName).anyMatch(y -> y.equals(tagContent));
            if (containTag) {
                bookNames.add(book.getBookName().fullName);
            }
        }
        return bookNames;
    }

    /**
     * Ensures {@code selectedBook} is a valid book in {@code filteredBooks}.
     */
    private void ensureSelectedBookIsValid(ListChangeListener.Change<? extends Book> change) {
        while (change.next()) {
            if (selectedBook.getValue() == null) {
                // null is always a valid selected book, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedBookReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedBook.getValue());
            if (wasSelectedBookReplaced) {
                // Update selectedBook to its new value.
                int index = change.getRemoved().indexOf(selectedBook.getValue());
                selectedBook.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedBookRemoved = change.getRemoved().stream()
                    .anyMatch(removedBook -> selectedBook.getValue().isSameBook(removedBook));
            if (wasSelectedBookRemoved) {
                // Select the book that came before it in the list,
                // or clear the selection if there is no such book.
                selectedBook.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    /**
     * Ensures {@code selectedBook} is a valid book in {@code filteredBooks}.
     */
    private void ensureSelectedReviewIsValid(ListChangeListener.Change<? extends Review> change) {
        while (change.next()) {
            if (selectedReview.getValue() == null) {
                // null is always a valid selected review, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedReviewReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedReview.getValue());
            if (wasSelectedReviewReplaced) {
                // Update selectedReview to its new value.
                int index = change.getRemoved().indexOf(selectedReview.getValue());
                selectedReview.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedReviewRemoved = change.getRemoved().stream()
                    .anyMatch(removedReview -> selectedReview.getValue().equals(removedReview));
            if (wasSelectedReviewRemoved) {
                // Select the review that came before it in the list,
                // or clear the selection if there is no such review.
                selectedReview.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedBookShelf.equals(other.versionedBookShelf)
                && userPrefs.equals(other.userPrefs)
                && filteredBooks.equals(other.filteredBooks)
                && Objects.equals(selectedBook.get(), other.selectedBook.get());
    }

    @Override
    public String toString() {
        return filteredBooks.toString() + filteredReviews.toString();
    }
}
