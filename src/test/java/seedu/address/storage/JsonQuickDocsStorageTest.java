package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.EVE;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientQuickDocs;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.QuickDocs;

public class JsonQuickDocsStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonQuickDocsStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readQuickDocs_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readQuickDocs(null);
    }

    private java.util.Optional<QuickDocs> readQuickDocs(String filePath) throws Exception {
        return new JsonQuickDocsStorage(Paths.get(filePath)).readQuickDocs(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readQuickDocs("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readQuickDocs("notJsonFormatQuickDocs.json");
    }

    @Test
    public void readQuickDocs_invalidPatientQuickDocs_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readQuickDocs("invalidPatientQuickDocs.json");
    }

    @Test
    public void readQuickDocs_invalidAndValidPatientAddressBook_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readQuickDocs("invalidAndValidPatientQuickDocs.json");
    }

    @Test
    public void readAndSaveQuickDocs_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempQuickDocs.json");
        QuickDocs original = getTypicalPatientQuickDocs();
        JsonQuickDocsStorage jsonQuickDocsStorage = new JsonQuickDocsStorage(filePath);

        // Save in new file and read back
        jsonQuickDocsStorage.saveQuickDocs(original, filePath);
        QuickDocs readBack = jsonQuickDocsStorage.readQuickDocs(filePath).get();
        assertEquals(original, readBack);

        // Modify data, overwrite exiting file, and read back
        original.getPatientManager().addPatient(EVE);
        original.getPatientManager().deletePatientByNric(ALICE.getNric().toString());
        jsonQuickDocsStorage.saveQuickDocs(original, filePath);
        readBack = jsonQuickDocsStorage.readQuickDocs(filePath).get();
        assertEquals(original, readBack);

        // Save and read without specifying file path
        original.getPatientManager().addPatient(ALICE);
        jsonQuickDocsStorage.saveQuickDocs(original); // file path not specified
        readBack = jsonQuickDocsStorage.readQuickDocs().get(); // file path not specified
        assertEquals(original, readBack);
    }

    @Test
    public void saveQuickDocs_nullQuickDocs_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveQuickDocs(null, "SomeFile.json");
    }

    /**
     * Saves {@code quickDocs} at the specified {@code filePath}.
     */
    private void saveQuickDocs(QuickDocs quickDocs, String filePath) {
        try {
            new JsonQuickDocsStorage(Paths.get(filePath))
                    .saveQuickDocs(quickDocs, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveQuickDocs_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveQuickDocs(new QuickDocs(), null);
    }
}
