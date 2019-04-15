package seedu.address.model.review;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * Represents a Review of a Restaurant in the food diary.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Review {

    // Identity fields
    private final Timestamp timeStamp;

    // Data fields
    private final Entry entry;
    private final Rating rating;

    /**
     * Every field must be present and not null.
     * This is the constructor for new Reviews.
     */
    public Review(Entry entry, Rating rating) {
        Date date = new Date();
        this.timeStamp = new Timestamp(date.getTime());
        this.entry = entry;
        this.rating = rating;
        requireAllNonNull(timeStamp, entry, rating);
    }

    /**
     * Every field must be present and not null.
     * This is the constructor for SampleDataUtil & JsonAdaptedReview models.
     */
    public Review(Entry entry, Rating rating, Timestamp timeStamp) {
        requireAllNonNull(timeStamp, entry, rating);
        this.timeStamp = timeStamp;
        this.entry = entry;
        this.rating = rating;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public Entry getEntry() {
        return entry;
    }

    public Rating getRating() {
        return rating;
    }

    /**
     * Returns true if both reviews of the same Entry have the same identity field, TimeStamp.
     * This defines a weaker notion of equality between two reviews.
     */
    public boolean isSameReview(Review otherReview) {
        if (otherReview == this) {
            return true;
        }

        return otherReview != null
                && otherReview.getEntry().equals(getEntry())
                && otherReview.getTimeStamp().equals(getTimeStamp());
    }

    /**
     * Returns true if both reviews have the same identity and data fields.
     * This defines a stronger notion of equality between two reviews.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Review)) {
            return false;
        }

        Review otherReview = (Review) other;
        return otherReview.getEntry().equals(getEntry())
                && otherReview.getRating().equals(getRating())
                && otherReview.getTimeStamp().equals(getTimeStamp());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(timeStamp, entry, rating);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTimeStamp().toLocalDateTime().toString())
                .append(" Rating: ")
                .append(getRating())
                .append(" Entry: ")
                .append(getEntry());
        return builder.toString();
    }
}
