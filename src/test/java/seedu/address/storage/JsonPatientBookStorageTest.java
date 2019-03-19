package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.HARRY;
import static seedu.address.testutil.TypicalPatients.IGRIS;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;

import seedu.address.model.PatientBook;
import seedu.address.model.ReadOnlyPatientBook;

public class JsonPatientBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPatientBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readPatientBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readPatientBook(null);
    }

    private java.util.Optional<ReadOnlyPatientBook> readPatientBook(String filePath) throws Exception {
        return new JsonPatientBookStorage(Paths.get(filePath)).readPatientBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPatientBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readPatientBook("notJsonFormatPatientBook.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readPatientBook_invalidPatientPatientBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readPatientBook("invalidPatientPatientBook.json");
    }

    @Test
    public void readPatientBook_invalidAndValidPatientPatientBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readPatientBook("invalidAndValidPatientPatientBook.json");
    }

    @Test
    public void readAndSavePatientBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempPatientBook.json");
        PatientBook original = getTypicalPatientBook();
        JsonPatientBookStorage jsonPatientBookStorage = new JsonPatientBookStorage(filePath);

        // Save in new file and read back
        jsonPatientBookStorage.savePatientBook(original, filePath);
        ReadOnlyPatientBook readBack = jsonPatientBookStorage.readPatientBook(filePath).get();
        assertEquals(original, new PatientBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatient(HARRY);
        original.removePatient(ALICE);
        jsonPatientBookStorage.savePatientBook(original, filePath);
        readBack = jsonPatientBookStorage.readPatientBook(filePath).get();
        assertEquals(original, new PatientBook(readBack));

        // Save and read without specifying file path
        original.addPatient(IGRIS);
        jsonPatientBookStorage.savePatientBook(original); // file path not specified
        readBack = jsonPatientBookStorage.readPatientBook().get(); // file path not specified
        assertEquals(original, new PatientBook(readBack));

    }

    @Test
    public void savePatientBook_nullPatientBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePatientBook(null, "SomeFile.json");
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void savePatientBook(ReadOnlyPatientBook addressBook, String filePath) {
        try {
            new JsonPatientBookStorage(Paths.get(filePath))
                    .savePatientBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePatientBook_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePatientBook(new PatientBook(), null);
    }
}
