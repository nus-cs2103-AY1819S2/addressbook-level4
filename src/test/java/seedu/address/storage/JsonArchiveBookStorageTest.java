package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.testutil.TypicalPersons.IDA;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;

public class JsonArchiveBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonArchiveBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readArchiveBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readArchiveBook(null);
    }

    private java.util.Optional<ReadOnlyAddressBook> readArchiveBook(String filePath) throws Exception {
        return new JsonArchiveBookStorage(Paths.get(filePath)).readArchiveBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readArchiveBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readArchiveBook("notJsonFormatArchiveBook.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readArchiveBook_invalidPersonArchiveBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readArchiveBook("invalidPersonArchiveBook.json");
    }

    @Test
    public void readArchiveBook_invalidAndValidPersonArchiveBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readArchiveBook("invalidAndValidPersonArchiveBook.json");
    }

    @Test
    public void readAndSaveArchiveBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempArchiveBook.json");
        AddressBook original = getTypicalAddressBook();
        JsonArchiveBookStorage jsonArchiveBookStorage = new JsonArchiveBookStorage(filePath);

        // Save in new file and read back
        jsonArchiveBookStorage.saveArchiveBook(original, filePath);
        ReadOnlyAddressBook readBack = jsonArchiveBookStorage.readArchiveBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonArchiveBookStorage.saveArchiveBook(original, filePath);
        readBack = jsonArchiveBookStorage.readArchiveBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonArchiveBookStorage.saveArchiveBook(original); // file path not specified
        readBack = jsonArchiveBookStorage.readArchiveBook().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void saveArchiveBook_nullArchiveBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveArchiveBook(null, "SomeFile.json");
    }

    /**
     * Saves {@code archiveBook} at the specified {@code filePath}.
     */
    private void saveArchiveBook(ReadOnlyAddressBook archiveBook, String filePath) {
        try {
            new JsonArchiveBookStorage(Paths.get(filePath))
                    .saveArchiveBook(archiveBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveArchiveBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveArchiveBook(new AddressBook(), null);
    }
}
