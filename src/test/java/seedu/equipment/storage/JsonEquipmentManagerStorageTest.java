package seedu.equipment.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.equipment.testutil.TypicalEquipments.ANCHORVALECC;
import static seedu.equipment.testutil.TypicalEquipments.HOON;
import static seedu.equipment.testutil.TypicalEquipments.IDA;
import static seedu.equipment.testutil.TypicalEquipments.getTypicalEquipmentManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.equipment.commons.exceptions.DataConversionException;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.model.ReadOnlyEquipmentManager;

public class JsonEquipmentManagerStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonEquipmentManagerStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readEquipmentManager_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readEquipmentManager(null);
    }

    private java.util.Optional<ReadOnlyEquipmentManager> readEquipmentManager(String filePath) throws Exception {
        return new JsonEquipmentManagerStorage(Paths.get(filePath))
                .readEquipmentManager(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readEquipmentManager("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readEquipmentManager("notJsonFormatEquipmentManager.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readEquipmentManager_invalidEquipmentEquipmentManager_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readEquipmentManager("invalidEquipmentEM.json");
    }

    @Test
    public void readEquipmentManager_invalidAndValidEquipment_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readEquipmentManager("invalidAndValidEquipmentEM.json");
    }

    @Test
    public void readAndSaveEquipmentManager_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempEquipmentManager.json");
        EquipmentManager original = getTypicalEquipmentManager();
        JsonEquipmentManagerStorage jsonEquipmentManagerStorage = new JsonEquipmentManagerStorage(filePath);

        // Save in new file and read back
        jsonEquipmentManagerStorage.saveEquipmentManager(original, filePath);
        ReadOnlyEquipmentManager readBack = jsonEquipmentManagerStorage.readEquipmentManager(filePath).get();
        assertEquals(original, new EquipmentManager(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ANCHORVALECC);
        jsonEquipmentManagerStorage.saveEquipmentManager(original, filePath);
        readBack = jsonEquipmentManagerStorage.readEquipmentManager(filePath).get();
        assertEquals(original, new EquipmentManager(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonEquipmentManagerStorage.saveEquipmentManager(original); // file path not specified
        readBack = jsonEquipmentManagerStorage.readEquipmentManager().get(); // file path not specified
        assertEquals(original, new EquipmentManager(readBack));

    }

    @Test
    public void saveEquipmentManager_nullEquipmentManager_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEquipmentManager(null, "SomeFile.json");
    }

    /**
     * Saves {@code equipmentManager} at the specified {@code filePath}.
     */
    private void saveEquipmentManager(ReadOnlyEquipmentManager equipmentManager, String filePath) {
        try {
            new JsonEquipmentManagerStorage(Paths.get(filePath))
                    .saveEquipmentManager(equipmentManager, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveEquipmentManager_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveEquipmentManager(new EquipmentManager(), null);
    }
}
