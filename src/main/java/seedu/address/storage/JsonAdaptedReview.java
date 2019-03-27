package seedu.address.storage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.book.Review;
import seedu.address.model.book.ReviewTitle;

/**
 * Jackson-friendly version of {@link Review}.
 */
class JsonAdaptedReview {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Review's %s field is missing!";

    public final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final String title;
    private final String message;
    private final String date;

    /**
     * Constructs a {@code JsonAdaptedReview} with the given book details.
     */
    @JsonCreator
    public JsonAdaptedReview(@JsonProperty("title") String reviewTitle, @JsonProperty("message") String reviewMessage,
                             @JsonProperty("date") String dateCreated) {
        this.title = reviewTitle;
        this.message = reviewMessage;
        this.date = dateCreated;
    }

    /**
     * Converts a given {@code Book} into this class for Jackson use.
     */
    public JsonAdaptedReview(Review source) {
        title = source.getTitle().fullName;
        message = source.getContent();
        date = source.getDate();
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

        if (message == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Review Content"));
        }
        final String modelReviewMessage = message;

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Review Date"));
        }
        final String modelDateCreated = date;

        return new Review(modelReviewTitle, modelDateCreated, modelReviewMessage);
    }

}
