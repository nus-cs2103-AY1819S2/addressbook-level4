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
import seedu.address.model.book.Review;

/**
 * An Immutable BookShelf that is serializable to JSON format.
 */
@JsonRootName(value = "bookshelf")
class JsonSerializableBookShelf {

    public static final String MESSAGE_DUPLICATE_BOOK = "Book list contains duplicate book(s).";

    private final List<JsonAdaptedBook> books = new ArrayList<>();
    private final List<JsonAdaptedReview> reviews = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableBookShelf} with the given books.
     */
    @JsonCreator
    public JsonSerializableBookShelf(@JsonProperty("books") List<JsonAdaptedBook> books,
            @JsonProperty("reviews") List<JsonAdaptedReview> reviews) {
        this.books.addAll(books);
        this.reviews.addAll(reviews);
    }

    /**
     * Converts a given {@code ReadOnlyBookShelf} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableBookShelf}.
     */
    public JsonSerializableBookShelf(ReadOnlyBookShelf source) {
        books.addAll(source.getBookList().stream().map(JsonAdaptedBook::new).collect(Collectors.toList()));
        reviews.addAll(source.getReviewList().stream().map(JsonAdaptedReview::new).collect(Collectors.toList()));
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
        for (JsonAdaptedReview jsonAdaptedReview : reviews) {
            Review review = jsonAdaptedReview.toModelType();
            if (bookShelf.hasReview(review)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_BOOK);
            }
            bookShelf.addReview(review);
        }
        return bookShelf;
    }

}
