package seedu.travel.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.travel.testutil.TypicalPlaces.ALICE;
import static seedu.travel.testutil.TypicalPlaces.HOON;
import static seedu.travel.testutil.TypicalPlaces.IDA;
import static seedu.travel.testutil.TypicalPlaces.getTypicalTravelBuddy;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.travel.commons.exceptions.DataConversionException;
import seedu.travel.model.ReadOnlyTravelBuddy;
import seedu.travel.model.TravelBuddy;

public class JsonTravelBuddyStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTravelBuddyStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTravelBuddy_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTravelBuddy(null);
    }

    private java.util.Optional<ReadOnlyTravelBuddy> readTravelBuddy(String filePath) throws Exception {
        return new JsonTravelBuddyStorage(Paths.get(filePath)).readTravelBuddy(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readTravelBuddy("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTravelBuddy("notJsonFormatTravelBuddy.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readTravelBuddy_invalidPlaceTravelBuddy_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTravelBuddy("invalidPlaceTravelBuddy.json");
    }

    @Test
    public void readTravelBuddy_invalidAndValidPlaceTravelBuddy_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTravelBuddy("invalidAndValidPlaceTravelBuddy.json");
    }

    @Test
    public void readAndSaveTravelBuddy_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempTravelBuddy.json");
        TravelBuddy original = getTypicalTravelBuddy();
        JsonTravelBuddyStorage jsonTravelBuddyStorage = new JsonTravelBuddyStorage(filePath);

        // Save in new file and read back
        jsonTravelBuddyStorage.saveTravelBuddy(original, filePath);
        ReadOnlyTravelBuddy readBack = jsonTravelBuddyStorage.readTravelBuddy(filePath).get();
        assertEquals(original, new TravelBuddy(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPlace(HOON);
        original.removePlace(ALICE);
        jsonTravelBuddyStorage.saveTravelBuddy(original, filePath);
        readBack = jsonTravelBuddyStorage.readTravelBuddy(filePath).get();
        assertEquals(original, new TravelBuddy(readBack));

        // Save and read without specifying file path
        original.addPlace(IDA);
        jsonTravelBuddyStorage.saveTravelBuddy(original); // file path not specified
        readBack = jsonTravelBuddyStorage.readTravelBuddy().get(); // file path not specified
        assertEquals(original, new TravelBuddy(readBack));

    }

    @Test
    public void saveTravelBuddy_nullTravelBuddy_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTravelBuddy(null, "SomeFile.json");
    }

    /**
     * Saves {@code travelBuddy} at the specified {@code filePath}.
     */
    private void saveTravelBuddy(ReadOnlyTravelBuddy travelBuddy, String filePath) {
        try {
            new JsonTravelBuddyStorage(Paths.get(filePath))
                    .saveTravelBuddy(travelBuddy, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTravelBuddy_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTravelBuddy(new TravelBuddy(), null);
    }
}
