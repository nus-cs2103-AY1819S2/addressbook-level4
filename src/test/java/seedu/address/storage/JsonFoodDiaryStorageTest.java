package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalRestaurants.ALICE;
import static seedu.address.testutil.TypicalRestaurants.HOON;
import static seedu.address.testutil.TypicalRestaurants.IDA;
import static seedu.address.testutil.TypicalRestaurants.getTypicalFoodDiary;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.FoodDiary;
import seedu.address.model.ReadOnlyFoodDiary;

public class JsonFoodDiaryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonFoodDiaryStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readFoodDiary_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readFoodDiary(null);
    }

    private java.util.Optional<ReadOnlyFoodDiary> readFoodDiary(String filePath) throws Exception {
        return new JsonFoodDiaryStorage(Paths.get(filePath)).readFoodDiary(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFoodDiary("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readFoodDiary("notJsonFormatFoodDiary.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readFoodDiary_invalidRestaurantFoodDiary_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readFoodDiary("invalidRestaurantFoodDiary.json");
    }

    @Test
    public void readFoodDiary_invalidAndValidRestaurantFoodDiary_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readFoodDiary("invalidAndValidRestaurantFoodDiary.json");
    }

    @Test
    public void readAndSaveFoodDiary_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempFoodDiary.json");
        FoodDiary original = getTypicalFoodDiary();
        JsonFoodDiaryStorage jsonFoodDiaryStorage = new JsonFoodDiaryStorage(filePath);

        // Save in new file and read back
        jsonFoodDiaryStorage.saveFoodDiary(original, filePath);
        ReadOnlyFoodDiary readBack = jsonFoodDiaryStorage.readFoodDiary(filePath).get();
        assertEquals(original, new FoodDiary(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addRestaurant(HOON);
        original.removeRestaurant(ALICE);
        jsonFoodDiaryStorage.saveFoodDiary(original, filePath);
        readBack = jsonFoodDiaryStorage.readFoodDiary(filePath).get();
        assertEquals(original, new FoodDiary(readBack));

        // Save and read without specifying file path
        original.addRestaurant(IDA);
        jsonFoodDiaryStorage.saveFoodDiary(original); // file path not specified
        readBack = jsonFoodDiaryStorage.readFoodDiary().get(); // file path not specified
        assertEquals(original, new FoodDiary(readBack));

    }

    @Test
    public void saveFoodDiary_nullFoodDiary_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveFoodDiary(null, "SomeFile.json");
    }

    /**
     * Saves {@code foodDiary} at the specified {@code filePath}.
     */
    private void saveFoodDiary(ReadOnlyFoodDiary foodDiary, String filePath) {
        try {
            new JsonFoodDiaryStorage(Paths.get(filePath))
                    .saveFoodDiary(foodDiary, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFoodDiary_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveFoodDiary(new FoodDiary(), null);
    }
}
