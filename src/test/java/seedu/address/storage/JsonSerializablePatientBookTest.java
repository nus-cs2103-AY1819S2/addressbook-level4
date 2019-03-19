package seedu.address.storage;

import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.PatientBook;
import seedu.address.testutil.TypicalPatients;

public class JsonSerializablePatientBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePatientBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPatientsBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPatientBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePatientBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPatientsFile_success() throws Exception {
        JsonSerializablePatientBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializablePatientBook.class).get();
        PatientBook patientBookFromFile = dataFromFile.toModelType();
        PatientBook typicalPatientsPatientBook = TypicalPatients.getTypicalPatientBook();
        assertTrue(patientBookFromFile.arePatientsSame(typicalPatientsPatientBook));

    }

    @Test
    public void toModelType_invalidPatientFile_throwsIllegalValueException() throws Exception {
        JsonSerializablePatientBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializablePatientBook.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePatients_throwsIllegalValueException() throws Exception {
        JsonSerializablePatientBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializablePatientBook.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializablePatientBook.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
