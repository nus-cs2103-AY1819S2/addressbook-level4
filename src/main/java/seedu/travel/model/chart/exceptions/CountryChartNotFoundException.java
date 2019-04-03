package seedu.travel.model.chart.exceptions;

/**
 * Signals that the operation is unable to find the specified country chart.
 */
public class CountryChartNotFoundException extends RuntimeException {
    public CountryChartNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CountryChartNotFoundException} with the specified detail {@code message}
     * and {@code cause}.
     */
    public CountryChartNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
