package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.book.Book;
import seedu.address.model.book.Review;
import seedu.address.model.tag.Tag;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Book> PREDICATE_SHOW_ALL_BOOKS = unused -> true;
    Predicate<Review> PREDICATE_SHOW_ALL_REVIEWS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' book shelf file path.
     */
    Path getBookShelfFilePath();

    /**
     * Sets the user prefs' book shelf file path.
     */
    void setBookShelfFilePath(Path bookShelfFilePath);

    /**
     * Replaces book shelf data with the data in {@code bookShelf}.
     */
    void setBookShelf(ReadOnlyBookShelf bookShelf);

    /** Returns the BookShelf */
    ReadOnlyBookShelf getBookShelf();
    
    /**
     * Returns true if a book with the same identity as {@code book} exists in the book shelf.
     */
    boolean hasBook(Book book);

    /**
     * Returns true if a book with the same identity as {@code book} exists in the book shelf.
     */
    boolean hasReview(Review review);

    /**
     * Deletes the given book.
     * The book must exist in the book shelf.
     */
    void deleteBook(Book target);

    /**
     * Deletes the given book.
     * The book must exist in the book shelf.
     */
    void deleteReview(Review target);

    /**
     * Adds the given book.
     * {@code book} must not already exist in the book shelf.
     */
    void addBook(Book book);

    /**
     * Adds the given book.
     * {@code book} must not already exist in the book shelf.
     */
    void addReview(Review review);

    /**
     * Replaces the given book {@code target} with {@code editedBook}.
     * {@code target} must exist in the book shelf.
     * The book identity of {@code editedBook} must not be the same as another existing book in the book shelf.
     */
    void setBook(Book target, Book editedBook);

    /** Returns an unmodifiable view of the filtered book list */
    ObservableList<Book> getFilteredBookList();

    /** Returns an unmodifiable view of the filtered book list */
    ObservableList<Review> getFilteredReviewList();

    /**
     * Updates the filter of the filtered book list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookList(Predicate<Book> predicate);

    /**
     * Updates the filter of the filtered book list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReviewList(Predicate<Review> predicate);

    /**
     * Returns true if the model has previous book shelf states to restore.
     */
    boolean canUndoBookShelf();

    /**
     * Returns true if the model has undone book shelf states to restore.
     */
    boolean canRedoBookShelf();

    /**
     * Restores the model's book shelf to its previous state.
     */
    void undoBookShelf();

    /**
     * Restores the model's book shelf to its previously undone state.
     */
    void redoBookShelf();

    /**
     * Saves the current book shelf state for undo/redo.
     */
    void commitBookShelf();

    /**
     * Selected book in the filtered book list.
     * null if no book is selected.
     */
    ReadOnlyProperty<Book> selectedBookProperty();

    /**
     * Selected book in the filtered book list.
     * null if no book is selected.
     */
    ReadOnlyProperty<Review> selectedReviewProperty();

    /**
     * Returns the selected book in the filtered book list.
     * null if no book is selected.
     */
    Book getSelectedBook();

    /**
     * Returns the selected review in the filtered review list.
     * null if no review is selected.
     */
    Review getSelectedReview();

    /**
     * Sets the selected book in the filtered book list.
     */
    void setSelectedBook(Book book);

    /**
     * Sets the selected book in the filtered review list.
     */
    void setSelectedReview(Review review);


    /**
     * Delete the tag{@code tag} of all contacts in the phone book
     */
    void deleteTag(Tag tag);

    /**
     * Sort the Book list into different order
     */
    void sortBook(String type, String order);
}
