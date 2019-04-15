package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalBooks.BOOKTHIEF;
import static seedu.address.testutil.TypicalBooks.CS;
import static seedu.address.testutil.TypicalBooks.SECRETLIFE;
import static seedu.address.testutil.TypicalBooks.getTypicalBookShelf;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.BookShelf;
import seedu.address.model.ReadOnlyBookShelf;

public class JsonBookShelfStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonBookShelfStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readBookShelf_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readBookShelf(null);
    }

    private java.util.Optional<ReadOnlyBookShelf> readBookShelf(String filePath) throws Exception {
        return new JsonBookShelfStorage(Paths.get(filePath)).readBookShelf(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readBookShelf("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readBookShelf("notJsonFormatBookShelf.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readBookShelf_invalidBookBookShelf_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readBookShelf("invalidBookBookShelf.json");
    }

    @Test
    public void readBookShelf_invalidAndValidBookBookShelf_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readBookShelf("invalidAndValidBookBookShelf.json");
    }

    @Test
    public void readAndSaveBookShelf_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempBookShelf.json");
        BookShelf original = getTypicalBookShelf();
        JsonBookShelfStorage jsonBookShelfStorage = new JsonBookShelfStorage(filePath);

        // Save in new file and read back
        jsonBookShelfStorage.saveBookShelf(original, filePath);
        ReadOnlyBookShelf readBack = jsonBookShelfStorage.readBookShelf(filePath).get();
        assertEquals(original, new BookShelf(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addBook(SECRETLIFE);
        original.removeBook(BOOKTHIEF);
        jsonBookShelfStorage.saveBookShelf(original, filePath);
        readBack = jsonBookShelfStorage.readBookShelf(filePath).get();
        assertEquals(original, new BookShelf(readBack));

        // Save and read without specifying file path
        original.addBook(CS);
        jsonBookShelfStorage.saveBookShelf(original); // file path not specified
        readBack = jsonBookShelfStorage.readBookShelf().get(); // file path not specified
        assertEquals(original, new BookShelf(readBack));

    }

    @Test
    public void saveBookShelf_nullBookShelf_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveBookShelf(null, "SomeFile.json");
    }

    /**
     * Saves {@code BookShelf} at the specified {@code filePath}.
     */
    private void saveBookShelf(ReadOnlyBookShelf bookShelf, String filePath) {
        try {
            new JsonBookShelfStorage(Paths.get(filePath))
                    .saveBookShelf(bookShelf, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveBookShelf_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveBookShelf(new BookShelf(), null);
    }
}
