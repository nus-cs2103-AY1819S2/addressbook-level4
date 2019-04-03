package seedu.travel.model.chart;

import java.util.Objects;

import seedu.travel.model.place.Rating;

/**
 * Represents a rating chart in TravelBuddy.
 */
public class RatingChart {
    private Rating rating;
    private int total;

    public RatingChart(Rating rating, int total) {
        this.rating = rating;
        this.total = total;
    }

    public Rating getChartRating() {
        return rating;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Returns true if both rating charts have the same identity and data fields.
     * This defines a stronger notion of equality between two rating charts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RatingChart)) {
            return false;
        }

        RatingChart otherPlace = (RatingChart) other;
        return otherPlace.getChartRating().equals(getChartRating())
                && otherPlace.getTotal() == getTotal();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(rating, total);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Rating: ")
                .append(getChartRating())
                .append(" Total: ")
                .append(getTotal());
        return builder.toString();
    }
}
