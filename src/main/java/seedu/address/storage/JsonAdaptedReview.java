package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewTitle;

/**
 * Jackson-friendly version of {@link Review}.
 */
class JsonAdaptedReview {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Review's %s field is missing!";
    private final String title;
    private final String bookName;
    private final String message;
    private final String date;

    /**
     * Constructs a {@code JsonAdaptedReview} with the given book details.
     */
    @JsonCreator
    public JsonAdaptedReview(@JsonProperty("title") String reviewTitle, @JsonProperty("bookname") String reviewBookName,
                             @JsonProperty("message") String reviewMessage, @JsonProperty("date") String dateCreated) {
        this.title = reviewTitle;
        this.bookName = reviewBookName;
        this.message = reviewMessage;
        this.date = dateCreated;
    }

    /**
     * Converts a given {@code Book} into this class for Jackson use.
     */
    public JsonAdaptedReview(Review source) {
        title = source.getTitle().fullName;
        bookName = source.getBookName().fullName;
        message = source.getReviewMessage();
        date = source.getDateCreated();
    }

    /**
     * Converts this Jackson-friendly adapted review object into the model's {@code Review} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted review.
     */
    public Review toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ReviewTitle.class.getSimpleName()));
        }
        if (!ReviewTitle.isValidReviewTitle(title)) {
            throw new IllegalValueException(ReviewTitle.MESSAGE_CONSTRAINTS);
        }
        final ReviewTitle modelReviewTitle = new ReviewTitle(title);

        if (bookName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BookName.class.getSimpleName()));
        }
        if (!BookName.isValidBookName(bookName)) {
            throw new IllegalValueException(BookName.MESSAGE_CONSTRAINTS);
        }
        final BookName modelBookName = new BookName(bookName);

        if (message == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Review Content"));
        }
        final String modelReviewMessage = message;

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Review Date"));
        }
        final String modelDateCreated = date;

        return new Review(modelReviewTitle, modelBookName, modelDateCreated, modelReviewMessage);
    }

}
