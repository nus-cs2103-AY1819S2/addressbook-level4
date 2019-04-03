package seedu.travel.storage;

import static seedu.travel.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private final Path backupCountryChartFilePath;
    private final Path backupRatingChartFilePath;
    private final Path backupYearChartFilePath;

    public JsonChartBookStorage(Path countryChartFilePath, Path ratingChartFilePath, Path yearChartFilePath) {
        this.countryChartFilePath = countryChartFilePath;
        this.ratingChartFilePath = ratingChartFilePath;
        this.yearChartFilePath = yearChartFilePath;
        this.backupCountryChartFilePath = Paths.get(countryChartFilePath.toString() + ".backup");
        this.backupRatingChartFilePath = Paths.get(ratingChartFilePath.toString() + ".backup");
        this.backupYearChartFilePath = Paths.get(yearChartFilePath.toString() + ".backup");
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
    public void saveCountryChart(ReadOnlyCountryChart countryChart) {
        saveCountryChart(countryChart, countryChartFilePath);
    }

    /**
     * Similar to {@link #saveCountryChart(ReadOnlyCountryChart)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveCountryChart(ReadOnlyCountryChart countryChart, Path filePath) {
        requireAllNonNull(countryChart, filePath);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(String.valueOf(filePath));
            gson.toJson(countryChart, fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

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
        } catch (IOException e) {
            logger.warning(e.getMessage());
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
        } catch (IOException e) {
            logger.warning(e.getMessage());
        }
    }

    @Override
    public void backupCountryChart(ReadOnlyCountryChart countryChart) {
        saveCountryChart(countryChart, backupCountryChartFilePath);
    }

    @Override
    public void backupRatingChart(ReadOnlyRatingChart ratingChart) {
        saveRatingChart(ratingChart, backupRatingChartFilePath);
    }

    @Override
    public void backupYearChart(ReadOnlyYearChart yearChart) {
        saveYearChart(yearChart, backupYearChartFilePath);
    }
}
