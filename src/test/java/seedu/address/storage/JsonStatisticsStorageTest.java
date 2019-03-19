package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE1;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE2;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE3;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.statistics.ReadOnlyStatistics;
import seedu.address.model.statistics.Statistics;

public class JsonStatisticsStorageTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonStatisticsStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readStatistics_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readStatistics(null);
    }

    private java.util.Optional<ReadOnlyStatistics> readStatistics(String filePath) throws Exception {
        return new JsonStatisticsStorage(Paths.get(filePath)).readStatistics(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder) : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readStatistics("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readStatistics("notJsonFormatStatistics.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readStatistics_invalidDailyRevenue_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readStatistics("invalidDailyRevenue.json");
    }

    @Test
    public void readStatistics_invalidAndValidDailyRevenue_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readStatistics("invalidAndValidDailyRevenue.json");
    }

    @Test
    public void readAndSaveStatistics_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempStatistics.json");
        Statistics original = getTypicalRestOrRant().getStatistics();
        JsonStatisticsStorage jsonStatisticsStorage = new JsonStatisticsStorage(filePath);

        // Save in new file and read back
        jsonStatisticsStorage.saveStatistics(original, filePath);
        ReadOnlyStatistics readBack = jsonStatisticsStorage.readStatistics(filePath).get();
        assertEquals(original, new Statistics(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addDailyRevenue(DAILY_REVENUE1);
        original.removeDailyRevenue(DAILY_REVENUE2);
        jsonStatisticsStorage.saveStatistics(original, filePath);
        readBack = jsonStatisticsStorage.readStatistics(filePath).get();
        assertEquals(original, new Statistics(readBack));

        // Save and read without specifying file path
        original.addDailyRevenue(DAILY_REVENUE3);
        jsonStatisticsStorage.saveStatistics(original); // file path not specified
        readBack = jsonStatisticsStorage.readStatistics().get(); // file path not specified
        assertEquals(original, new Statistics(readBack));

        jsonStatisticsStorage.backupStatistics(original);
        readBack = jsonStatisticsStorage.readStatistics().get();
        assertEquals(original, new Statistics(readBack));

    }

    @Test
    public void saveStatistics_nullStatistics_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveStatistics(null, "SomeFile.json");
    }

    /**
     * Saves {@code restOrRant} at the specified {@code filePath}.
     */
    private void saveStatistics(ReadOnlyStatistics statistics, String filePath) {
        try {
            new JsonStatisticsStorage(Paths.get(filePath))
                    .saveStatistics(statistics, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveStatistics_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveStatistics(new Statistics(), null);
    }
}
