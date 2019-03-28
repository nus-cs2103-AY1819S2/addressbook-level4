package seedu.address.testutil;

import seedu.address.model.BookShelf;
import seedu.address.model.book.Book;

/**
 * A utility class to help with building BookShelf objects.
 * Example usage: <br>
 *     {@code BookShelf ab = new BookShelfBuilder().withBook(Book book).build();}
 */
public class BookShelfBuilder {

    private BookShelf bookShelf;

    public BookShelfBuilder() {
        bookShelf = new BookShelf();
    }

    public BookShelfBuilder(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    /**
     * Adds a new {@code Book} to the {@code BookShelf} that we are building.
     */
    public BookShelfBuilder withBook(Book book) {
        bookShelf.addBook(book);
        return this;
    }

    public BookShelf build() {
        return bookShelf;
    }
}
