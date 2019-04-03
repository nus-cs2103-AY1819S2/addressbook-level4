package seedu.travel.model.chart.exceptions;

/**
 * Signals that the operation is unable to find the specified rating chart.
 */
public class RatingChartNotFoundException extends RuntimeException {
    public RatingChartNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code RatingChartNotFoundException} with the specified detail {@code message}
     * and {@code cause}.
     */
    public RatingChartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
