package seedu.travel.model.chart.exceptions;

/**
 * Signals that the operation is unable to find the specified rating chart.
 */
public class RatingChartNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code RatingChartNotFoundException} with the specified detail {@code message}.
     */
    public RatingChartNotFoundException(String message) {
        super(message);
    }
}
