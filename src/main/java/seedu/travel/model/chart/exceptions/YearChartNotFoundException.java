package seedu.travel.model.chart.exceptions;

/**
 * Signals that the operation is unable to find the specified year chart.
 */
public class YearChartNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@code YearChartNotFoundException} with the specified detail {@code message}.
     */
    public YearChartNotFoundException(String message) {
        super(message);
    }
}
