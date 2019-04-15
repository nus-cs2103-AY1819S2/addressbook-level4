package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalMedicines.HYDROCHLOROTHIAZIDE;
import static seedu.address.testutil.TypicalMedicines.NAPROXEN;
import static seedu.address.testutil.TypicalMedicines.PARACETAMOL;
import static seedu.address.testutil.TypicalMedicines.getTypicalInventory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Inventory;
import seedu.address.model.ReadOnlyInventory;

public class JsonInventoryStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonInventoryStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readInventory_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readInventory(null);
    }

    private java.util.Optional<ReadOnlyInventory> readInventory(String filePath) throws Exception {
        return new JsonInventoryStorage(Paths.get(filePath)).readInventory(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readInventory("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readInventory("notJsonFormatInventory.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readInventory_invalidMedicineInventory_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readInventory("invalidMedicineInventory.json");
    }

    @Test
    public void readInventory_invalidAndValidMedicineInventory_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readInventory("invalidAndValidMedicineInventory.json");
    }

    @Test
    public void readAndSaveInventory_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempInventory.json");
        Inventory original = getTypicalInventory();
        JsonInventoryStorage jsonInventoryStorage = new JsonInventoryStorage(filePath);

        // Save in new file and read back
        jsonInventoryStorage.saveInventory(original, filePath);
        ReadOnlyInventory readBack = jsonInventoryStorage.readInventory(filePath).get();
        assertEquals(original, new Inventory(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addMedicine(NAPROXEN);
        original.removeMedicine(PARACETAMOL);
        jsonInventoryStorage.saveInventory(original, filePath);
        readBack = jsonInventoryStorage.readInventory(filePath).get();
        assertEquals(original, new Inventory(readBack));

        // Save and read without specifying file path
        original.addMedicine(HYDROCHLOROTHIAZIDE);
        jsonInventoryStorage.saveInventory(original); // file path not specified
        readBack = jsonInventoryStorage.readInventory().get(); // file path not specified
        assertEquals(original, new Inventory(readBack));

    }

    @Test
    public void saveInventory_nullInventory_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveInventory(null, "SomeFile.json");
    }

    /**
     * Saves {@code inventory} at the specified {@code filePath}.
     */
    private void saveInventory(ReadOnlyInventory inventory, String filePath) {
        try {
            new JsonInventoryStorage(Paths.get(filePath))
                    .saveInventory(inventory, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveInventory_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveInventory(new Inventory(), null);
    }
}
