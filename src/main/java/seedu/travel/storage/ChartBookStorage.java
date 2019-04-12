package seedu.travel.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.travel.model.ReadOnlyCountryChart;
import seedu.travel.model.ReadOnlyRatingChart;
import seedu.travel.model.ReadOnlyYearChart;

/**
 * Represents a storage for ChartBook.
 */
public interface ChartBookStorage {
    /**
     * Returns the file path of the country data.
     */
    Path getCountryChartFilePath();

    /**
     * Returns the file path of the rating data.
     */
    Path getRatingChartFilePath();

    /**
     * Returns the file path of the year data.
     */
    Path getYearChartFilePath();

    /**
     * Returns country chart data as a {@link ReadOnlyCountryChart}.
     */
    List<ReadOnlyCountryChart> readCountryChart();

    /**
     * @see #readCountryChart()
     */
    List<ReadOnlyCountryChart> readCountryChart(Path filePath);

    /**
     * Returns rating chart data as a {@link ReadOnlyRatingChart}.
     */
    List<ReadOnlyRatingChart> readRatingChart();

    /**
     * @see #readRatingChart()
     */
    List<ReadOnlyRatingChart> readRatingChart(Path filePath);

    /**
     * Returns year chart data as a {@link ReadOnlyYearChart}.
     */
    List<ReadOnlyYearChart> readYearChart();

    /**
     * @see #readYearChart()
     */
    List<ReadOnlyYearChart> readYearChart(Path filePath);

    /**
     * Saves the given {@link ReadOnlyCountryChart} to the storage.
     * @param countryChart cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCountryChart(ReadOnlyCountryChart countryChart) throws IOException;

    /**
     * @see #saveCountryChart(ReadOnlyCountryChart)
     */
    void saveCountryChart(ReadOnlyCountryChart countryChart, Path filePath) throws IOException;

    /**
     * Saves the given {@link ReadOnlyCountryChart} to the storage.
     * @param ratingChart cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRatingChart(ReadOnlyRatingChart ratingChart) throws IOException;

    /**
     * @see #saveRatingChart(ReadOnlyRatingChart)
     */
    void saveRatingChart(ReadOnlyRatingChart ratingChart, Path filePath) throws IOException;

    /**
     * Saves the given {@link ReadOnlyCountryChart} to the storage.
     * @param yearChart cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveYearChart(ReadOnlyYearChart yearChart) throws IOException;

    /**
     * @see #saveYearChart(ReadOnlyYearChart)
     */
    void saveYearChart(ReadOnlyYearChart yearChart, Path filePath) throws IOException;
}
