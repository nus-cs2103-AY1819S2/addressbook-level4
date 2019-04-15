package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.book.Book;
import seedu.address.model.book.Review;
import seedu.address.model.book.UniqueBookList;
import seedu.address.model.book.UniqueReviewList;
import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the book-shelf level
 * Duplicates are not allowed (by .isSameBook comparison)
 */
public class BookShelf implements ReadOnlyBookShelf {
    private final UniqueBookList books;
    private final UniqueReviewList reviews;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        books = new UniqueBookList();
        reviews = new UniqueReviewList();
    }

    public BookShelf() {}

    /**
     * Creates an BookShelf using the Books in the {@code toBeCopied}
     */
    public BookShelf(ReadOnlyBookShelf toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations
    /**
     * Replaces the contents of the book list with {@code books}.
     * {@code books} must not contain duplicate books.
     */
    public void setBooks(List<Book> books) {
        this.books.setBooks(books);
        indicateModified();
    }

    /**
     * Replaces the contents of the review list with {@code reviews}.
     * {@code reviews} must not contain duplicate reviews.
     */
    public void setReviews(List<Review> reviews) {
        this.reviews.setReviews(reviews);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code BookShelf} with {@code newData}.
     */
    public void resetData(ReadOnlyBookShelf newData) {
        requireNonNull(newData);
        setBooks(newData.getBookList());
        setReviews(newData.getReviewList());
    }

    //// book-level operations
    /**
     * Returns true if a book with the same identity as {@code book} exists in the book shelf.
     */
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return books.contains(book);
    }

    /**
     * Returns true if a review with the same identity as {@code review} exists in the book shelf.
     */
    public boolean hasReview(Review review) {
        requireNonNull(review);
        return reviews.contains(review);
    }

    /**
     * Adds a book to the book shelf.
     * The book must not already exist in the book shelf.
     */
    public void addBook(Book b) {
        books.add(b);
        indicateModified();
    }

    /**
     * Adds a review to the book shelf.
     * The book must not already exist in the book shelf.
     */
    public void addReview(Review review) {
        reviews.add(review);
        indicateModified();
    }

    /**
     * Replaces the given book {@code target} in the list with {@code editedBook}.
     * {@code target} must exist in the book shelf.
     * The book identity of {@code editedBook} must not be the same as another existing book in the book shelf.
     */
    public void setBook(Book target, Book editedBook) {
        requireNonNull(editedBook);
        books.setBook(target, editedBook);
        indicateModified();
    }

    /**
     * Replaces the given review {@code target} in the list with {@code editedReview}.
     * {@code target} must exist in the book shelf.
     * The review identity of {@code editedReview} must not be the same as another existing review in the book shelf.
     */
    public void setReview(Review target, Review editedReview) {
        requireNonNull(editedReview);
        reviews.setReview(target, editedReview);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code BookShelf}.
     * {@code key} must exist in the book shelf.
     */
    public void removeBook(Book key) {
        books.remove(key);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code BookShelf}.
     * {@code key} must exist in the book shelf.
     */
    public void removeReview(Review key) {
        reviews.remove(key);
        indicateModified();
    }

    /**
     * Removes {@code tag} from a {@code book}
     */
    private void removeTagFromBook(Tag tag, Book book) {
        Set<Tag> tags = new HashSet<Tag>(book.getTags());
        if (!tags.remove(tag)) {
            return;
        }

        Book newBook = new Book(book.getBookName(), book.getAuthor(),
                book.getRating(), tags);

        setBook(book, newBook);
    }

    /**
     * Remove {@code tag} from all book in the list
     */
    public void removeTag(Tag tag) {
        for (Book p: books) {
            removeTagFromBook(tag, p);
        }
    }

    /**
     * Sorts {@code books} in bookCard with {@code order}
     * @param types sorting method
     * @param mainOrder order that apply to all types
     * @param subOrder order that apply only to corresponding type.
     */
    public void sort(List<String> types, String mainOrder, Map<String, String> subOrder) {
        books.sortBooks(types, mainOrder, subOrder);
        indicateModified();
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the book shelf has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return books.asUnmodifiableObservableList().size() + " books";
    }

    @Override
    public ObservableList<Book> getBookList() {
        return books.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Review> getReviewList() {
        return reviews.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookShelf // instanceof handles nulls
                && books.equals(((BookShelf) other).books)
                && books.equals(((BookShelf) other).books))
                && reviews.equals(((BookShelf) other).reviews);
    }

    @Override
    public int hashCode() {
        return books.hashCode();
    }
}
