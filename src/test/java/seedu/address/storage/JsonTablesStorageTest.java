package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import static seedu.address.testutil.TypicalRestOrRant.TABLE1;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalTables;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.table.ReadOnlyTables;
import seedu.address.model.table.Table;
import seedu.address.model.table.Tables;

public class JsonTablesStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonTablesStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readTables_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readTables(null);
    }

    private java.util.Optional<ReadOnlyTables> readTables(String filePath) throws Exception {
        return new JsonTablesStorage(Paths.get(filePath)).readTables(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readTables_missingFile_emptyResult() throws Exception {
        assertFalse(readTables("NonExistentFile.json").isPresent());
    }

    @Test
    public void readTables_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readTables("notJsonFormatTables.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readTables_invalidTables_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTables("invalidTables.json");
    }

    @Test
    public void readTables_invalidAndValidTables_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readTables("invalidAndValidTables.json");
    }

    @Test
    public void readAndSaveTables_allInTable_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempTables.json");
        Tables original = new Tables();
        for (Table table : getTypicalTables()) {
            original.addTable(table);
        }
        JsonTablesStorage jsonTablesStorage = new JsonTablesStorage(filePath);

        // Save in new file and read back
        jsonTablesStorage.saveTables(original, filePath);
        ReadOnlyTables readBack = jsonTablesStorage.readTables(filePath).get();
        assertEquals(original, new Tables(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addTable(TABLE1);
        original.removeTable(TABLE1);
        jsonTablesStorage.saveTables(original, filePath);
        readBack = jsonTablesStorage.readTables(filePath).get();
        assertEquals(original, new Tables(readBack));

        // Save and read without specifying file path
        original.addTable(TABLE1);
        jsonTablesStorage.saveTables(original); // file path not specified
        readBack = jsonTablesStorage.readTables().get(); // file path not specified
        assertEquals(original, new Tables(readBack));

        jsonTablesStorage.backupTables(original);
        readBack = jsonTablesStorage.readTables().get();
        assertEquals(original, new Tables(readBack));

    }

    @Test
    public void saveTables_nullTables_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTables(null, "SomeFile.json");
    }

    /**
     * Saves {@code tables} at the specified {@code filePath}.
     */
    private void saveTables(ReadOnlyTables tables, String filePath) {
        try {
            new JsonTablesStorage(Paths.get(filePath))
                    .saveTables(tables, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveTables_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveTables(new Tables(), null);
    }
}
