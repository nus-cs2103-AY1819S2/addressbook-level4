package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalRestOrRant.AGLIO_OLIO;
import static seedu.address.testutil.TypicalRestOrRant.FRENCH_FRIES;
import static seedu.address.testutil.TypicalRestOrRant.HONEY_MILK_TEA;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRestOrRant;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.menu.Menu;
import seedu.address.model.menu.ReadOnlyMenu;



public class JsonMenuStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonMenuStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readMenu_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readMenu(null);
    }

    private java.util.Optional<ReadOnlyMenu> readMenu(String filePath) throws Exception {
        return new JsonMenuStorage(Paths.get(filePath)).readMenu(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder) : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readMenu("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readMenu("notJsonFormatMenu.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readMenu_invalidMenuItem_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readMenu("invalidMenuItem.json");
    }

    @Test
    public void readMenu_invalidAndValidMenuItem_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readMenu("invalidAndValidMenuItem.json");
    }

    @Test
    public void readAndSaveMenu_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempMenu.json");
        Menu original = getTypicalRestOrRant().getMenu();
        JsonMenuStorage jsonMenuStorage = new JsonMenuStorage(filePath);

        // Save in new file and read back
        jsonMenuStorage.saveMenu(original, filePath);
        ReadOnlyMenu readBack = jsonMenuStorage.readMenu(filePath).get();
        assertEquals(original, new Menu(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addMenuItem(HONEY_MILK_TEA);
        original.removeMenuItem(FRENCH_FRIES);
        jsonMenuStorage.saveMenu(original, filePath);
        readBack = jsonMenuStorage.readMenu(filePath).get();
        assertEquals(original, new Menu(readBack));

        // Save and read without specifying file path
        original.addMenuItem(AGLIO_OLIO);
        jsonMenuStorage.saveMenu(original); // file path not specified
        readBack = jsonMenuStorage.readMenu().get(); // file path not specified
        assertEquals(original, new Menu(readBack));

        jsonMenuStorage.backupMenu(original);
        readBack = jsonMenuStorage.readMenu().get();
        assertEquals(original, new Menu(readBack));

    }

    @Test
    public void saveMenu_nullMenu_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveMenu(null, "SomeFile.json");
    }

    /**
     * Saves {@code restOrRant} at the specified {@code filePath}.
     */
    private void saveMenu(ReadOnlyMenu menu, String filePath) {
        try {
            new JsonMenuStorage(Paths.get(filePath)).saveMenu(menu, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveMenu_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveMenu(new Menu(), null);
    }

}
