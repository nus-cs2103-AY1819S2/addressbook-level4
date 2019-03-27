package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Rating;
import seedu.address.model.book.Review;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Book}.
 */
class JsonAdaptedBook {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Book's %s field is missing!";

    private final String bookname;
    private final String author;
    private final String rating;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedReview> reviewed = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedBook} with the given book details.
     */
    @JsonCreator
    public JsonAdaptedBook(@JsonProperty("bookname") String bookname, @JsonProperty("author") String author,
                           @JsonProperty("rating") String rating, @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                           @JsonProperty("reviews") List<JsonAdaptedReview> reviewed) {
        this.bookname = bookname;
        this.author = author;
        this.rating = rating;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (reviewed != null) {
            this.reviewed.addAll(reviewed);
        }
    }

    /**
     * Converts a given {@code Book} into this class for Jackson use.
     */
    public JsonAdaptedBook(Book source) {
        bookname = source.getBookName().fullName;
        author = source.getAuthor().fullName;
        rating = source.getRating().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        reviewed.addAll(source.getReviews().stream()
                .map(JsonAdaptedReview::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted book object into the model's {@code Book} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted book.
     */
    public Book toModelType() throws IllegalValueException {
        final List<Tag> bookTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            bookTags.add(tag.toModelType());
        }

        final List<Review> bookReviews = new ArrayList<>();
        for (JsonAdaptedReview review : reviewed) {
            bookReviews.add(review.toModelType());
        }

        if (bookname == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                BookName.class.getSimpleName()));
        }
        if (!BookName.isValidBookName(bookname)) {
            throw new IllegalValueException(BookName.MESSAGE_CONSTRAINTS);
        }
        final BookName modelBookName = new BookName(bookname);

        if (author == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Author.class.getSimpleName()));
        }
        if (!Author.isValidAuthor(author)) {
            throw new IllegalValueException(Author.MESSAGE_CONSTRAINTS);
        }
        final Author modelAuthor = new Author(author);

        if (rating == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName()));
        }
        if (!Rating.isValidRating(rating)) {
            throw new IllegalValueException(Rating.MESSAGE_CONSTRAINTS);
        }
        final Rating modelRating = new Rating(rating);

        final Set<Tag> modelTags = new HashSet<>(bookTags);
        final Set<Review> modelReviews = new HashSet<>(bookReviews);
        return new Book(modelBookName, modelAuthor, modelRating, modelTags, modelReviews);
    }

}
