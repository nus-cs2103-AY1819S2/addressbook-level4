package seedu.address.testutil;

import java.sql.Timestamp;

import seedu.address.model.review.Entry;
import seedu.address.model.review.Rating;
import seedu.address.model.review.Review;

/**
 * A utility class to help with building Review objects.
 */

public class ReviewBuilder {

    public static final String DEFAULT_ENTRY = "Standard restaurant";
    public static final String DEFAULT_RATING = "3";

    private Entry entry;
    private Rating rating;
    private Timestamp timestamp;

    public ReviewBuilder() {
        entry = new Entry(DEFAULT_ENTRY);
        rating = new Rating(DEFAULT_RATING);
        timestamp = null;
    }

    /**
     * Sets the {@code Entry} of the {@code Review} that we are building.
     */
    public ReviewBuilder withEntry(String entry) {
        this.entry = new Entry(entry);
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code Review} that we are building.
     */
    public ReviewBuilder withRating(String rating) {
        this.rating = new Rating(rating);
        return this;
    }

    /**
     * Sets the {@code Timestamp} of the {@code Review} that we are building.
     */
    public ReviewBuilder withTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    /**
     * Constructs the {@code Review} with attributes in ReviewBuilder.
     */
    public Review build() {
        if (timestamp == null) {
            return new Review(entry, rating);
        }
        return new Review(entry, rating, timestamp);
    }
}
