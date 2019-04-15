package seedu.address.model.restaurant;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import seedu.address.model.review.Review;

/**
 * Represents a summary of a Restaurant in the food diary.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Summary {
    public static final Float NO_RATING = (float) -1;
    private static final DecimalFormat ONE_DP = new DecimalFormat("#.#");

    // Data fields
    private Float avgRating;
    private Integer totalVisits;

    /**
     * Every field must be present and not null.
     * This is the constructor for new Reviews.
     */
    public Summary(List<Review> reviews) {

        // Check if Restaurant has been reviewed before
        if (!reviews.isEmpty()) {
            this.totalVisits = computeTotalVisits(reviews);
            this.avgRating = computeAvgRating(reviews, totalVisits);
        } else {
            this.totalVisits = 0;
            this.avgRating = NO_RATING;
        }

        requireAllNonNull(totalVisits, avgRating);
    }

    /**
     * Returns the total number of visits to the restaurant based on
     * the number of reviews it has.
     */
    public int computeTotalVisits(List<Review> reviews) {
        return reviews.size();
    }

    /**
     * Computes the average ratings from all the reviews of the restaurant.
     */
    public float computeAvgRating(List<Review> reviews, int numVisits) {
        Iterator<Review> iterator = reviews.listIterator();
        float sumRatings = 0;

        while (iterator.hasNext()) {
            sumRatings += iterator.next().getRating().toFloat();
        }

        return (sumRatings / numVisits);
    }

    public Integer getTotalVisits() {
        return this.totalVisits;
    }

    public Float getAvgRating() {
        return Float.valueOf(ONE_DP.format(this.avgRating));
    }

    /**
     * Returns true if both summaries belong to the same Restaurant and have
     * the same totalVisits and avgRating.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Summary)) {
            return false;
        }

        Summary otherSummary = (Summary) other;
        return otherSummary.getAvgRating().equals(getAvgRating())
                && otherSummary.getTotalVisits().equals(getTotalVisits());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(avgRating, totalVisits);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getAvgRating())
                .append(" from ")
                .append(getTotalVisits())
                .append(" visit(s)");
        return builder.toString();
    }
}
