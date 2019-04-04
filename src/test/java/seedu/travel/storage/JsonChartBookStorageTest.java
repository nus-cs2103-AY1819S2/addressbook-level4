package seedu.travel.storage;

import static java.util.Objects.requireNonNull;
import static seedu.travel.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import seedu.travel.model.ChartBook;
import seedu.travel.model.ReadOnlyCountryChart;
import seedu.travel.model.ReadOnlyRatingChart;
import seedu.travel.model.ReadOnlyYearChart;

public class JsonChartBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonChartBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws JsonSyntaxException {

        thrown.expect(JsonSyntaxException.class);
        readCountryChart("notJsonFormat.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readCountryChart_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        readCountryChart(null);
    }

    /**
     * Reads the {@code CountryChart} data from the Json file path
     * @param filePath the place where the file is stored
     * @return a list of {@code CountryChart} data
     * @throws JsonSyntaxException due to Json formatting issues
     */
    @SuppressWarnings("unchecked")
    private List<ReadOnlyCountryChart> readCountryChart(String filePath) throws JsonSyntaxException {
        requireNonNull(filePath);

        Gson gson = new Gson();
        List<ReadOnlyCountryChart> countryList;
        try {
            FileReader fileReader = new FileReader(String.valueOf(addToTestDataPathIfNotNull(filePath)));
            countryList = gson.fromJson(fileReader, List.class);
        } catch (FileNotFoundException fnfe) {
            throw new AssertionError("There should not be an error in finding the file.", fnfe);
        }

        return countryList;
    }

    @Test
    public void readCountryChart_invalidCountryChart_throwDataConversionException() throws JsonSyntaxException {
        thrown.expect(JsonSyntaxException.class);
        readCountryChart("invalidCountryChart.json");
    }

    @Test
    public void readCountryChart_invalidAndValidCountryChart_throwDataConversionException()
            throws JsonSyntaxException {
        thrown.expect(JsonSyntaxException.class);
        readCountryChart("invalidAndValidCountryChart.json");
    }

    @Test
    public void readRatingChart_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        readCountryChart(null);
    }

    /**
     * Reads the {@code RatingChart} data from the Json file path
     * @param filePath the place where the file is stored
     * @return a list of {@code RatingChart} data
     * @throws JsonSyntaxException due to Json formatting issues
     */
    @SuppressWarnings("unchecked")
    private List<ReadOnlyRatingChart> readRatingChart(String filePath) throws JsonSyntaxException {
        requireNonNull(filePath);

        Gson gson = new Gson();
        List<ReadOnlyRatingChart> ratingList;
        try {
            FileReader fileReader = new FileReader(String.valueOf(addToTestDataPathIfNotNull(filePath)));
            ratingList = gson.fromJson(fileReader, List.class);
        } catch (FileNotFoundException fnfe) {
            throw new AssertionError("There should not be an error in finding the file.", fnfe);
        }

        return ratingList;
    }

    @Test
    public void readRatingChart_invalidRatingChart_throwDataConversionException() throws JsonSyntaxException {
        thrown.expect(JsonSyntaxException.class);
        readRatingChart("invalidRatingChart.json");
    }

    @Test
    public void readRatingChart_invalidAndValidRatingChart_throwDataConversionException()
            throws JsonSyntaxException {
        thrown.expect(JsonSyntaxException.class);
        readRatingChart("invalidAndValidRatingChart.json");
    }

    @Test
    public void readYearChart_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        readYearChart(null);
    }

    /**
     * Reads the {@code YearChart} data from the Json file path
     * @param filePath the place where the file is stored
     * @return a list of {@code YearChart} data
     * @throws JsonSyntaxException due to Json formatting issues
     */
    @SuppressWarnings("unchecked")
    private List<ReadOnlyYearChart> readYearChart(String filePath) throws JsonSyntaxException {
        requireNonNull(filePath);

        Gson gson = new Gson();
        List<ReadOnlyYearChart> yearList;
        try {
            FileReader fileReader = new FileReader(String.valueOf(addToTestDataPathIfNotNull(filePath)));
            yearList = gson.fromJson(fileReader, List.class);
        } catch (FileNotFoundException fnfe) {
            throw new AssertionError("There should not be an error in finding the file.", fnfe);
        }

        return yearList;
    }

    @Test
    public void readYearChart_invalidYearChart_throwDataConversionException() throws JsonSyntaxException {
        thrown.expect(JsonSyntaxException.class);
        readYearChart("invalidYearChart.json");
    }

    @Test
    public void readYearChart_invalidAndValidYearChart_throwDataConversionException()
            throws JsonSyntaxException {
        thrown.expect(JsonSyntaxException.class);
        readYearChart("invalidAndValidYearChart.json");
    }

    @Test
    public void saveCountryChart_nullCountryChart_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveCountryChart(null, "SomeFile.json");
    }

    /**
     * Saves {@code countryChart} at the specified {@code filePath}.
     */
    private void saveCountryChart(ReadOnlyCountryChart countryChart, String filePath) {
        requireAllNonNull(countryChart, filePath);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(String.valueOf(filePath));
            gson.toJson(countryChart, fileWriter);
            fileWriter.flush();
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCountryChart_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveCountryChart(new ChartBook(), null);
    }

    @Test
    public void saveRatingChart_nullRatingChart_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRatingChart(null, "SomeFile.json");
    }

    /**
     * Saves {@code ratingChart} at the specified {@code filePath}.
     */
    private void saveRatingChart(ReadOnlyRatingChart ratingChart, String filePath) {
        requireAllNonNull(ratingChart, filePath);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(String.valueOf(filePath));
            gson.toJson(ratingChart, fileWriter);
            fileWriter.flush();
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRatingChart_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRatingChart(new ChartBook(), null);
    }

    @Test
    public void saveYearChart_nullRatingChart_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveYearChart(null, "SomeFile.json");
    }

    /**
     * Saves {@code ratingChart} at the specified {@code filePath}.
     */
    private void saveYearChart(ReadOnlyYearChart yearChart, String filePath) {
        requireAllNonNull(yearChart, filePath);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileWriter fileWriter = new FileWriter(String.valueOf(filePath));
            gson.toJson(yearChart, fileWriter);
            fileWriter.flush();
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveYearChart_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveYearChart(new ChartBook(), null);
    }
}
