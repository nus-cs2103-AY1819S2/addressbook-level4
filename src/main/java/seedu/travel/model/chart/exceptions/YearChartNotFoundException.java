package seedu.travel.model.chart.exceptions;

/**
 * Signals that the operation is unable to find the specified year chart.
 */
public class YearChartNotFoundException extends RuntimeException {
    public YearChartNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code YearChartNotFoundException} with the specified detail {@code message} and {@code cause}.
     */
    public YearChartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
