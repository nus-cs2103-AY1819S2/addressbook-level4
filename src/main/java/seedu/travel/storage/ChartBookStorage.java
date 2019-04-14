package seedu.travel.storage;

import java.io.FileNotFoundException;
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
     * @throws FileNotFoundException if the file is not found.
     */
    List<ReadOnlyCountryChart> readCountryChart() throws FileNotFoundException;

    /**
     * @throws FileNotFoundException if the file is not found.
     * @see #readCountryChart()
     */
    List<ReadOnlyCountryChart> readCountryChart(Path filePath) throws FileNotFoundException;

    /**
     * Returns rating chart data as a {@link ReadOnlyRatingChart}.
     * @throws FileNotFoundException if the file is not found.
     */
    List<ReadOnlyRatingChart> readRatingChart() throws FileNotFoundException;

    /**
     * @throws FileNotFoundException if the file is not found.
     * @see #readRatingChart()
     */
    List<ReadOnlyRatingChart> readRatingChart(Path filePath) throws FileNotFoundException;

    /**
     * Returns year chart data as a {@link ReadOnlyYearChart}.
     * @throws FileNotFoundException if the file is not found.
     */
    List<ReadOnlyYearChart> readYearChart() throws FileNotFoundException;

    /**
     * @throws FileNotFoundException if the file is not found.
     * @see #readYearChart()
     */
    List<ReadOnlyYearChart> readYearChart(Path filePath) throws FileNotFoundException;

    /**
     * Saves the given {@link ReadOnlyCountryChart} to the storage.
     * @param countryChart cannot be null.
     */
    void saveCountryChart(ReadOnlyCountryChart countryChart);

    /**
     * @see #saveCountryChart(ReadOnlyCountryChart)
     */
    void saveCountryChart(ReadOnlyCountryChart countryChart, Path filePath);

    /**
     * Saves the given {@link ReadOnlyCountryChart} to the storage.
     * @param ratingChart cannot be null.
     */
    void saveRatingChart(ReadOnlyRatingChart ratingChart);

    /**
     * @see #saveRatingChart(ReadOnlyRatingChart)
     */
    void saveRatingChart(ReadOnlyRatingChart ratingChart, Path filePath);

    /**
     * Saves the given {@link ReadOnlyCountryChart} to the storage.
     * @param yearChart cannot be null.
     */
    void saveYearChart(ReadOnlyYearChart yearChart);

    /**
     * @see #saveYearChart(ReadOnlyYearChart)
     */
    void saveYearChart(ReadOnlyYearChart yearChart, Path filePath);
}
