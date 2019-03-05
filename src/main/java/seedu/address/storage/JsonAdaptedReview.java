package seedu.address.storage;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.review.Review;

/**
 * Jackson-friendly version of {@link Review}.
 */
class JsonAdaptedReview {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Review's %s field is missing!";

    private final String timeStamp;
    private final String entry;
    private final String rating;

    /**
     * Constructs a {@code JsonAdaptedReview} with the given review details.
     */
    @JsonCreator
    public JsonAdaptedReview(@JsonProperty("timeStamp") String timeStamp, @JsonProperty("entry") String entry,
                                 @JsonProperty("rating") String rating) {
        this.timeStamp = timeStamp;
        this.entry = entry;
        this.rating = rating;
    }

    /**
     * Converts a given {@code Review} into this class for Jackson use.
     */
    public JsonAdaptedReview(Review source) {
        timeStamp = source.getTimeStamp().toLocalDateTime().toString();
        entry = source.getEntry().toString();
        rating = source.getRating().toString();
    }

    /**
     * Converts this Jackson-friendly adapted review object into the model's {@code Review} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted review.
     */
    public Review toModelType() throws IllegalValueException {
        if (entry == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Entry.class.getSimpleName()));
        }
        if (!Entry.isValidEntry(entry)) {
            throw new IllegalValueException(Entry.MESSAGE_CONSTRAINTS);
        }
        final Entry modelEntry = new Entry(entry);

        if (rating == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName()));
        }
        if (!Rating.isValidRating(rating)) {
            throw new IllegalValueException(Rating.MESSAGE_CONSTRAINTS);
        }
        final Rating modelRating = new Rating(rating);

        final Timestamp modelTimeStamp = Timestamp.valueOf(LocalDateTime.parse(timeStamp));

        return new Review(modelEntry, modelRating, modelTimeStamp);
    }

}
