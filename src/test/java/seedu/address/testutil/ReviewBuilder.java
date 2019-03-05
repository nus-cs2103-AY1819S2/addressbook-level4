package seedu.address.testutil;

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

    public ReviewBuilder() {
        entry = new Entry(DEFAULT_ENTRY);
        rating = new Rating(DEFAULT_RATING);
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

    public Review build() {
        return new Review(entry, rating);
    }
}
