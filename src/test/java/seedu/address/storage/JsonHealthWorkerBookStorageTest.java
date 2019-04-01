package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalHealthWorkers.ANDY;
import static seedu.address.testutil.TypicalHealthWorkers.HOOK;
import static seedu.address.testutil.TypicalHealthWorkers.IVAN;
import static seedu.address.testutil.TypicalHealthWorkers.getTypicalHealthWorkerBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.HealthWorkerBook;
import seedu.address.model.ReadOnlyHealthWorkerBook;



public class JsonHealthWorkerBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonHealthWorkerBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readHealthWorkerBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readHealthWorkerBook(null);
    }

    private java.util.Optional<ReadOnlyHealthWorkerBook> readHealthWorkerBook(String filePath) throws Exception {
        return new JsonHealthWorkerBookStorage(Paths.get(filePath))
                .readHealthWorkerBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readHealthWorkerBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readHealthWorkerBook("notJsonFormatHealthWorkerBook.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readHealthWorkerBook_invalidPersonHealthWorkerBook_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readHealthWorkerBook("invalidPersonHealthWorkerBook.json"); //TODO regenerate the json files
    }

    @Test
    public void readHealthWorkerBook_invalidAndValidPersonHealthWorkerBook_throwDataConversionException()
            throws Exception {
        thrown.expect(DataConversionException.class);
        readHealthWorkerBook("invalidAndValidPersonHealthWorkerBook.json"); //TODO regenerate the json files
    }

    @Test
    public void readAndSaveHealthWorkerBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempHealthWorkerBook.json");
        HealthWorkerBook original = getTypicalHealthWorkerBook();
        JsonHealthWorkerBookStorage jsonHealthWorkerBookStorage = new JsonHealthWorkerBookStorage(filePath);

        // Save in new file and read back
        jsonHealthWorkerBookStorage.saveHealthWorkerBook(original, filePath);
        ReadOnlyHealthWorkerBook readBack = jsonHealthWorkerBookStorage.readHealthWorkerBook(filePath).get();
        assertEquals(original, new HealthWorkerBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addHealthWorker(HOOK);
        original.removeHealthWorker(ANDY);
        jsonHealthWorkerBookStorage.saveHealthWorkerBook(original, filePath);
        readBack = jsonHealthWorkerBookStorage.readHealthWorkerBook(filePath).get();
        assertEquals(original, new HealthWorkerBook(readBack));

        // Save and read without specifying file path
        original.addHealthWorker(IVAN);
        jsonHealthWorkerBookStorage.saveHealthWorkerBook(original); // file path not specified
        readBack = jsonHealthWorkerBookStorage.readHealthWorkerBook().get(); // file path not specified
        assertEquals(original, new HealthWorkerBook(readBack));

    }

    @Test
    public void saveHealthWorkerBook_nullHealthWorkerBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveHealthWorkerBook(null, "SomeFile.json");
    }

    /**
     * Saves {@code healthWorkerBook} at the specified {@code filePath}.
     */
    private void saveHealthWorkerBook(ReadOnlyHealthWorkerBook healthWorkerBook, String filePath) {
        try {
            new JsonHealthWorkerBookStorage(Paths.get(filePath))
                    .saveHealthWorkerBook(healthWorkerBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveHealthWorkerBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveHealthWorkerBook(new HealthWorkerBook(), null);
    }
}
