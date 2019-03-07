package seedu.address.testutil;

import seedu.address.model.BookShelf;
import seedu.address.model.person.Person;
import seedu.address.model.book.Book;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code BookShelf ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private BookShelf bookShelf;

    public AddressBookBuilder() {
        bookShelf = new BookShelf();
    }

    public AddressBookBuilder(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }

    /**
     * Adds a new {@code Person} to the {@code BookShelf} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        bookShelf.addPerson(person);
        return this;
    }

    public AddressBookBuilder withBook(Book book) {
        bookShelf.addBook(book);
        return this;
    }

    public BookShelf build() {
        return bookShelf;
    }
}
