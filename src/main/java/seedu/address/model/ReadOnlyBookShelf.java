package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.book.Book;
import seedu.address.model.book.Review;

/**
 * Unmodifiable view of an book shelf
 */
public interface ReadOnlyBookShelf extends Observable {

    /**
     * Returns an unmodifiable view of the book list.
     * This list will not contain any duplicate books.
     */
    ObservableList<Book> getBookList();

    /**
     * Returns an unmodifiable view of the review list.
     * This list will not contain any duplicate reviews.
     */
    ObservableList<Review> getReviewList();
}
