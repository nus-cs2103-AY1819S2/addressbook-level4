package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalEmployees.ALICE;
import static seedu.address.testutil.TypicalEmployees.HOON;
import static seedu.address.testutil.TypicalEmployees.IDA;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.PocketProject;
import seedu.address.model.ReadOnlyPocketProject;
import seedu.address.testutil.TestUtil;
import seedu.address.testutil.TypicalProjects;

public class JsonPocketProjectStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
                                                                "data", "JsonPocketProjectStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readPocketProject_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readPocketProject(null);
    }

    private java.util.Optional<ReadOnlyPocketProject> readPocketProject(String filePath) throws Exception {
        return new JsonPocketProjectStorage(Paths.get(filePath))
                    .readPocketProject(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPocketProject("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readPocketProject("notJsonFormatPocketProject.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readPocketProject_invalidEmployeePocketProject_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readPocketProject("invalidEmployeePocketProject.json");
    }

    @Test
    public void readPocketProject_invalidAndValidEmployeePocketProject_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readPocketProject("invalidAndValidEmployeePocketProject.json");
    }

    @Test
    public void readAndSavePocketProject_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempPocketProject.json");
        PocketProject original = TestUtil.typicalPocketProject();
        JsonPocketProjectStorage jsonPocketProjectStorage = new JsonPocketProjectStorage(filePath);

        // Save in new file and read back
        jsonPocketProjectStorage.savePocketProject(original, filePath);
        ReadOnlyPocketProject readBack = jsonPocketProjectStorage.readPocketProject(filePath).get();
        assertEquals(original, new PocketProject(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addEmployee(HOON);
        original.addProject(TypicalProjects.PROJECT_HOON);
        original.removeEmployee(ALICE);
        original.removeProject(TypicalProjects.PROJECT_ALICE);
        jsonPocketProjectStorage.savePocketProject(original, filePath);
        readBack = jsonPocketProjectStorage.readPocketProject(filePath).get();
        assertEquals(original, new PocketProject(readBack));

        // Save and read without specifying file path
        original.addEmployee(IDA);
        original.addProject(TypicalProjects.PROJECT_IDA);
        jsonPocketProjectStorage.savePocketProject(original); // file path not specified
        readBack = jsonPocketProjectStorage.readPocketProject().get(); // file path not specified
        assertEquals(original, new PocketProject(readBack));

    }

    @Test
    public void savePocketProject_nullPocketProject_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePocketProject(null, "SomeFile.json");
    }

    /**
     * Saves {@code pocketProject} at the specified {@code filePath}.
     */
    private void savePocketProject(ReadOnlyPocketProject pocketProject, String filePath) {
        try {
            new JsonPocketProjectStorage(Paths.get(filePath))
                    .savePocketProject(pocketProject, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void savePocketProject_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        savePocketProject(new PocketProject(), null);
    }
}
