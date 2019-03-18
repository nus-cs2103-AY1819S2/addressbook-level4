package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyBookShelf;
import seedu.address.model.book.Book;

/**
 * An Immutable BookShelf that is serializable to JSON format.
 */
@JsonRootName(value = "bookshelf")
class JsonSerializableBookShelf {

    public static final String MESSAGE_DUPLICATE_BOOK = "Book list contains duplicate book(s).";

    private final List<JsonAdaptedBook> books = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBookShelf} with the given books.
     */
    @JsonCreator
    public JsonSerializableBookShelf(@JsonProperty("books") List<JsonAdaptedBook> books) {
        this.books.addAll(books);
    }

    /**
     * Converts a given {@code ReadOnlyBookShelf} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBookShelf}.
     */
    public JsonSerializableBookShelf(ReadOnlyBookShelf source) {
        books.addAll(source.getBookList().stream().map(JsonAdaptedBook::new).collect(Collectors.toList()));
    }

    /**
     * Converts this book shelf into the model's {@code BookShelf} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public BookShelf toModelType() throws IllegalValueException {
        BookShelf bookShelf = new BookShelf();
        for (JsonAdaptedBook jsonAdaptedBook : books) {
            Book book = jsonAdaptedBook.toModelType();
            if (bookShelf.hasBook(book)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BOOK);
            }
            bookShelf.addBook(book);
        }
        return bookShelf;
    }

}
