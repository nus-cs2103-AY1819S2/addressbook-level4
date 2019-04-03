package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.HOON;
import static seedu.address.testutil.TypicalPatients.IDA;
import static seedu.address.testutil.TypicalPatients.getTypicalDocX;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DocX;
import seedu.address.model.ReadOnlyDocX;

public class JsonDocXStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonDocXStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readDocX_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readDocX(null);
    }

    private java.util.Optional<ReadOnlyDocX> readDocX(String filePath) throws Exception {
        return new JsonDocXStorage(Paths.get(filePath)).readDocX(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readDocX("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readDocX("notJsonFormatDocX.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readDocX_invalidPatientDocX_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readDocX("invalidPatientDocX.json");
    }

    @Test
    public void readDocX_invalidAndValidPatientDocX_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readDocX("invalidAndValidPatientDocX.json");
    }

    @Test
    public void readAndSaveDocX_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempDocX.json");
        DocX original = getTypicalDocX();
        JsonDocXStorage jsonDocXStorage = new JsonDocXStorage(filePath);

        // Save in new file and read back
        jsonDocXStorage.saveDocX(original, filePath);
        ReadOnlyDocX readBack = jsonDocXStorage.readDocX(filePath).get();
        assertEquals(original, new DocX(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatient(HOON);
        original.removePatient(ALICE);
        jsonDocXStorage.saveDocX(original, filePath);
        readBack = jsonDocXStorage.readDocX(filePath).get();
        assertEquals(original, new DocX(readBack));

        // Save and read without specifying file path
        original.addPatient(IDA);
        jsonDocXStorage.saveDocX(original); // file path not specified
        readBack = jsonDocXStorage.readDocX().get(); // file path not specified
        assertEquals(original, new DocX(readBack));

    }

    @Test
    public void saveDocX_nullDocX_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveDocX(null, "SomeFile.json");
    }

    /**
     * Saves {@code DocX} at the specified {@code filePath}.
     */
    private void saveDocX(ReadOnlyDocX docX, String filePath) {
        try {
            new JsonDocXStorage(Paths.get(filePath))
                    .saveDocX(docX, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveDocX_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveDocX(new DocX(), null);
    }
}
