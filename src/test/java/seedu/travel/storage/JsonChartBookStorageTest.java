package seedu.travel.storage;

import static org.junit.Assert.assertNotEquals;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import com.google.gson.JsonSyntaxException;

import seedu.travel.model.ChartBook;
import seedu.travel.model.ReadOnlyCountryChart;
import seedu.travel.model.ReadOnlyRatingChart;
import seedu.travel.model.ReadOnlyYearChart;

public class JsonChartBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonChartBookStorageTest");

    private static Path validCountryPath = Paths.get("src", "test", "data",
            "JsonChartBookStorageTest", "validCountryChart.json");
    private static Path validRatingPath = Paths.get("src", "test", "data",
            "JsonChartBookStorageTest", "validRatingChart.json");
    private static Path validYearPath = Paths.get("src", "test", "data",
            "JsonChartBookStorageTest", "validYearChart.json");


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
    public void read_notJsonFormat_exceptionThrown() throws JsonSyntaxException, FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(JsonSyntaxException.class);
        testChartBookStorage.readCountryChart(addToTestDataPathIfNotNull("notJsonFormat.json"));

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readCountryChart_nullFilePath_throwsNullPointerException() throws FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(NullPointerException.class);
        testChartBookStorage.readCountryChart(null);
    }

    @Test
    public void readCountryChart_invalidCountryChart_throwDataConversionException() throws JsonSyntaxException,
            FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(JsonSyntaxException.class);
        testChartBookStorage.readCountryChart(addToTestDataPathIfNotNull("invalidCountryChart.json"));
    }

    @Test
    public void readCountryChart_invalidAndValidCountryChart_throwDataConversionException()
            throws JsonSyntaxException, FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(JsonSyntaxException.class);
        testChartBookStorage.readCountryChart(
                addToTestDataPathIfNotNull("invalidAndValidCountryChart.json"));
    }

    @Test
    public void readCountryChart_invalidFile_throwFileNotFoundException() throws FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);

        thrown.expect(FileNotFoundException.class);
        testChartBookStorage.readCountryChart(
                addToTestDataPathIfNotNull("invalidFile.json"));
    }

    @Test
    public void readRatingChart_invalidFile_throwFileNotFoundException() throws FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);

        thrown.expect(FileNotFoundException.class);
        testChartBookStorage.readRatingChart(
                addToTestDataPathIfNotNull("invalidFile.json"));
    }

    @Test
    public void readCountryChart_validCountryChart_readSuccess() throws FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(validCountryPath,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        assertNotEquals(testChartBookStorage.readCountryChart(), new ArrayList<ReadOnlyCountryChart>());
    }

    @Test
    public void readRatingChart_nullFilePath_throwsNullPointerException() throws FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(NullPointerException.class);
        testChartBookStorage.readCountryChart(null);
    }

    @Test
    public void readRatingChart_invalidRatingChart_throwDataConversionException()
            throws JsonSyntaxException, FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(JsonSyntaxException.class);
        testChartBookStorage.readRatingChart(addToTestDataPathIfNotNull("invalidRatingChart.json"));
    }

    @Test
    public void readRatingChart_invalidAndValidRatingChart_throwDataConversionException()
            throws JsonSyntaxException, FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(JsonSyntaxException.class);
        testChartBookStorage.readRatingChart(
                addToTestDataPathIfNotNull("invalidAndValidRatingChart.json"));
    }

    @Test
    public void readRatingChart_validRatingChart_readSuccess() throws FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                validRatingPath, TEST_DATA_FOLDER);
        assertNotEquals(testChartBookStorage.readRatingChart(), new ArrayList<ReadOnlyRatingChart>());
    }

    @Test
    public void readYearChart_nullFilePath_throwsNullPointerException() throws FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(NullPointerException.class);
        testChartBookStorage.readYearChart(null);
    }

    @Test
    public void readYearChart_invalidYearChart_throwDataConversionException()
            throws JsonSyntaxException, FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(JsonSyntaxException.class);
        testChartBookStorage.readYearChart(addToTestDataPathIfNotNull("invalidYearChart.json"));
    }

    @Test
    public void readYearChart_invalidAndValidYearChart_throwDataConversionException()
            throws JsonSyntaxException, FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(JsonSyntaxException.class);
        testChartBookStorage.readYearChart(
                addToTestDataPathIfNotNull("invalidAndValidYearChart.json"));
    }

    @Test
    public void readYearChart_validYearChart_readSuccess() throws FileNotFoundException {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, validYearPath);
        assertNotEquals(testChartBookStorage.readYearChart(), new ArrayList<ReadOnlyYearChart>());
    }

    @Test
    public void saveCountryChart_nullCountryChart_throwsNullPointerException() {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(NullPointerException.class);
        testChartBookStorage.saveCountryChart(
                null, addToTestDataPathIfNotNull("validCountryChart.json"));
    }

    @Test
    public void saveCountryChart_nullFilePath_throwsNullPointerException() {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(NullPointerException.class);
        testChartBookStorage.saveCountryChart(new ChartBook(), null);
    }

    @Test
    public void saveRatingChart_nullRatingChart_throwsNullPointerException() {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(NullPointerException.class);
        testChartBookStorage.saveRatingChart(
                null, addToTestDataPathIfNotNull("validRatingChart.json"));
    }

    @Test
    public void saveRatingChart_nullFilePath_throwsNullPointerException() {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(NullPointerException.class);
        testChartBookStorage.saveRatingChart(new ChartBook(), null);
    }

    @Test
    public void saveYearChart_nullRatingChart_throwsNullPointerException() {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(NullPointerException.class);
        testChartBookStorage.saveYearChart(
                null, addToTestDataPathIfNotNull("validYearChart.json"));
    }

    @Test
    public void saveYearChart_nullFilePath_throwsNullPointerException() {
        JsonChartBookStorage testChartBookStorage = new JsonChartBookStorage(TEST_DATA_FOLDER,
                TEST_DATA_FOLDER, TEST_DATA_FOLDER);
        thrown.expect(NullPointerException.class);
        testChartBookStorage.saveYearChart(new ChartBook(), null);
    }
}
