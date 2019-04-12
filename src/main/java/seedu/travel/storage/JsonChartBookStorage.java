package seedu.travel.storage;

import static java.util.Objects.requireNonNull;
import static seedu.travel.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import seedu.travel.commons.core.LogsCenter;
import seedu.travel.model.ReadOnlyCountryChart;
import seedu.travel.model.ReadOnlyRatingChart;
import seedu.travel.model.ReadOnlyYearChart;

/**
 * A class to access ChartBook data stored as a json file on the hard disk.
 */
public class JsonChartBookStorage implements ChartBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonTravelBuddyStorage.class);

    private final Path countryChartFilePath;
    private final Path ratingChartFilePath;
    private final Path yearChartFilePath;

    public JsonChartBookStorage(Path countryChartFilePath, Path ratingChartFilePath, Path yearChartFilePath) {
        this.countryChartFilePath = countryChartFilePath;
        this.ratingChartFilePath = ratingChartFilePath;
        this.yearChartFilePath = yearChartFilePath;
    }

    @Override
    public Path getCountryChartFilePath() {
        return countryChartFilePath;
    }

    @Override
    public Path getRatingChartFilePath() {
        return ratingChartFilePath;
    }

    @Override
    public Path getYearChartFilePath() {
        return yearChartFilePath;
    }

    @Override
    public List<ReadOnlyCountryChart> readCountryChart() throws FileNotFoundException {
        return readCountryChart(countryChartFilePath);
    }

    /**
     * Similar to {@link #readCountryChart()}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ReadOnlyCountryChart> readCountryChart(Path filePath) throws FileNotFoundException {
        requireNonNull(filePath);
        Gson gson = new Gson();
        List<ReadOnlyCountryChart> countryList;
        FileReader fileReader = new FileReader(String.valueOf(filePath));
        countryList = gson.fromJson(fileReader, List.class);
        return countryList;
    }

    @Override
    public List<ReadOnlyRatingChart> readRatingChart() throws FileNotFoundException {
        return readRatingChart(ratingChartFilePath);
    }

    /**
     * Similar to {@link #readRatingChart()}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ReadOnlyRatingChart> readRatingChart(Path filePath) throws FileNotFoundException {
        requireNonNull(filePath);
        Gson gson = new Gson();
        List<ReadOnlyRatingChart> ratingList;
        FileReader fileReader = new FileReader(String.valueOf(filePath));
        ratingList = gson.fromJson(fileReader, List.class);
        return ratingList;
    }

    @Override
    public List<ReadOnlyYearChart> readYearChart() throws FileNotFoundException {
        return readYearChart(yearChartFilePath);
    }

    /**
     * Similar to {@link #readYearChart()}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ReadOnlyYearChart> readYearChart(Path filePath) throws FileNotFoundException {
        requireNonNull(filePath);
        Gson gson = new Gson();
        List<ReadOnlyYearChart> yearList;
        FileReader fileReader = new FileReader(String.valueOf(filePath));
        yearList = gson.fromJson(fileReader, List.class);
        return yearList;
    }

    @Override
    public void saveCountryChart(ReadOnlyCountryChart countryChart) {
        saveCountryChart(countryChart, countryChartFilePath);
    }

    /**
     * Similar to {@link #saveCountryChart(ReadOnlyCountryChart)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    // tag::saveCountryChart[]
    public void saveCountryChart(ReadOnlyCountryChart countryChart, Path filePath) {
        requireAllNonNull(countryChart, filePath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(String.valueOf(filePath));
            gson.toJson(countryChart, fileWriter);
            fileWriter.flush();
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
    }
    // end::saveCountryChart[]

    @Override
    public void saveRatingChart(ReadOnlyRatingChart ratingChart) {
        saveRatingChart(ratingChart, ratingChartFilePath);
    }

    /**
     * Similar to {@link #saveRatingChart(ReadOnlyRatingChart)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveRatingChart(ReadOnlyRatingChart ratingChart, Path filePath) {
        requireAllNonNull(ratingChart, filePath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(String.valueOf(filePath));
            gson.toJson(ratingChart, fileWriter);
            fileWriter.flush();
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
    }

    @Override
    public void saveYearChart(ReadOnlyYearChart yearChart) {
        saveYearChart(yearChart, yearChartFilePath);
    }

    /**
     * Similar to {@link #saveYearChart(ReadOnlyYearChart)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveYearChart(ReadOnlyYearChart yearChart, Path filePath) {
        requireAllNonNull(yearChart, filePath);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(String.valueOf(filePath));
            gson.toJson(yearChart, fileWriter);
            fileWriter.flush();
        } catch (IOException ioe) {
            logger.warning(ioe.getMessage());
        }
    }

}
