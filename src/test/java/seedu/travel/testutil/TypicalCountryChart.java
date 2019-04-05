package seedu.travel.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.travel.model.ChartBook;
import seedu.travel.model.chart.CountryChart;

/**
 * A utility class containing a list of {@code CountryChart} objects to be used in tests.
 */
public class TypicalCountryChart {

    public static final CountryChart ARGENTINA = new CountryChartBuilder()
            .withCountryCode("ARG")
            .withTotal("1")
            .build();

    public static final CountryChart BRAZIL = new CountryChartBuilder()
            .withCountryCode("BRA")
            .withTotal("2")
            .build();

    public static final CountryChart CANADA = new CountryChartBuilder()
            .withCountryCode("CAN")
            .withTotal("3")
            .build();

    public static final CountryChart DENMARK = new CountryChartBuilder()
            .withCountryCode("DNK")
            .withTotal("4")
            .build();

    public static final CountryChart ECUADOR = new CountryChartBuilder()
            .withCountryCode("ECU")
            .withTotal("5")
            .build();

    private TypicalCountryChart() {} // prevents instantiation

    /**
     * Returns an {@code ChartBook} with all the typical country chart.
     */
    public static ChartBook getTypicalChartBook() {
        ChartBook chartBook = new ChartBook();
        for (CountryChart countryChart : getTypicalCountryChart()) {
            chartBook.addCountryChart(countryChart);
        }
        return chartBook;
    }

    public static List<CountryChart> getTypicalCountryChart() {
        return new ArrayList<>(Arrays.asList(ARGENTINA, BRAZIL, CANADA, DENMARK, ECUADOR));
    }
}
