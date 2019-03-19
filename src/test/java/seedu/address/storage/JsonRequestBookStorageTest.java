package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
//import static seedu.address.testutil.TypicalRequests.ALICE_REQUEST;
//import static seedu.address.testutil.TypicalRequests.HOON_REQUEST;
//import static seedu.address.testutil.TypicalRequests.IDA_REQUEST;
import static seedu.address.testutil.TypicalRequests.getTypicalRequestBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;

import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.RequestBook;



public class JsonRequestBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonRequestBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readRequestBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readRequestBook(null);
    }

    private java.util.Optional<ReadOnlyRequestBook> readRequestBook(String filePath) throws Exception {
        return new JsonRequestBookStorage(Paths.get(filePath)).readRequestBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readRequestBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readRequestBook("notJsonFormatRequestBook.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readRequestBook_invalidPersonRequestBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRequestBook("invalidRequestRequestBook.json");
    }

    @Test
    public void readRequestBook_invalidAndValidPersonRequestBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readRequestBook("invalidAndValidRequestRequestBook.json");
    }

    @Test
    public void readAndSaveRequestBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempRequestBook.json");
        RequestBook original = getTypicalRequestBook();
        JsonRequestBookStorage jsonRequestBookStorage = new JsonRequestBookStorage(filePath);

        // Save in new file and read back
        jsonRequestBookStorage.saveRequestBook(original, filePath);
        ReadOnlyRequestBook readBack = jsonRequestBookStorage.readRequestBook(filePath).get();
        assertEquals(original, new RequestBook(readBack));

        /*
        // Modify data, overwrite exiting file, and read back
        original.addRequest(HOON_REQUEST);
        original.removeRequest(ALICE_REQUEST);
        jsonRequestBookStorage.saveRequestBook(original, filePath);
        readBack = jsonRequestBookStorage.readRequestBook(filePath).get();
        assertEquals(original, new RequestBook(readBack));

        // Save and read without specifying file path
        original.addRequest(IDA_REQUEST);
        jsonRequestBookStorage.saveRequestBook(original); // file path not specified
        readBack = jsonRequestBookStorage.readRequestBook().get(); // file path not specified
        assertEquals(original, new RequestBook(readBack));
        */

    }

    @Test
    public void saveRequestBook_nullRequestBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRequestBook(null, "SomeFile.json");
    }

    /**
     * Saves {@code requestBook} at the specified {@code filePath}.
     */
    private void saveRequestBook(ReadOnlyRequestBook requestBook, String filePath) {
        try {
            new JsonRequestBookStorage(Paths.get(filePath))
                    .saveRequestBook(requestBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveRequestBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveRequestBook(new RequestBook(), null);
    }
}
