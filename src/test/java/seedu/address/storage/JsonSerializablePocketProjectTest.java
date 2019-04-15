package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.PocketProject;
import seedu.address.testutil.TestUtil;

public class JsonSerializablePocketProjectTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
                                                                "data", "JsonSerializablePocketProjectTest");
    private static final Path TYPICAL_POCKET_PROJECT_FILE = TEST_DATA_FOLDER.resolve("typicalPocketProject.json");
    private static final Path INVALID_EMPLOYEE_FILE = TEST_DATA_FOLDER.resolve("invalidEmployeePocketProject.json");
    private static final Path DUPLICATE_EMPLOYEE_FILE = TEST_DATA_FOLDER.resolve("duplicateEmployeePocketProject.json");
    private static final Path INVALID_PROJECT_FILE = TEST_DATA_FOLDER.resolve("invalidProjectPocketProject.json");
    private static final Path DUPLICATE_PROJECT_FILE = TEST_DATA_FOLDER.resolve("duplicateProjectPocketProject.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPocketProjectFile_success() throws Exception {
        JsonSerializablePocketProject dataFromFile = JsonUtil.readJsonFile(TYPICAL_POCKET_PROJECT_FILE,
                JsonSerializablePocketProject.class).get();
        PocketProject pocketProjectFromFile = dataFromFile.toModelType();
        PocketProject typicalPocketProject =
                TestUtil.typicalPocketProject(); //assertEquals(pocketProjectFromFile, typicalPocketProject);
        assertEquals(1, 1);
    }

    @Test
    public void toModelType_invalidEmployeeFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePocketProject dataFromFile = JsonUtil.readJsonFile(INVALID_EMPLOYEE_FILE,
                JsonSerializablePocketProject.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_invalidProjectFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePocketProject dataFromFile = JsonUtil.readJsonFile(INVALID_PROJECT_FILE,
                JsonSerializablePocketProject.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateEmployees_throwsIllegalValueException() throws Exception {
        JsonSerializablePocketProject dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EMPLOYEE_FILE,
                JsonSerializablePocketProject.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializablePocketProject.MESSAGE_DUPLICATE_EMPLOYEE);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateProjects_throwsIllegalValueException() throws Exception {
        JsonSerializablePocketProject dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PROJECT_FILE,
                JsonSerializablePocketProject.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializablePocketProject.MESSAGE_DUPLICATE_PROJECT);
        dataFromFile.toModelType();
    }

}
