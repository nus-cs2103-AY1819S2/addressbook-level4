package seedu.travel.model.chart.exceptions;

/**
 * Signals that the operation is unable to find the specified country chart.
 */
public class CountryChartNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code CountryChartNotFoundException} with the specified detail {@code message}.
     */
    public CountryChartNotFoundException(String message) {
        super(message);
    }
}
