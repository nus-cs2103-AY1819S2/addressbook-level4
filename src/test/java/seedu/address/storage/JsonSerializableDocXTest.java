package seedu.address.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.DocX;
import seedu.address.testutil.TypicalDoctors;
import seedu.address.testutil.TypicalPatients;


public class JsonSerializableDocXTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableDocXTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPatientsDocX.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPatientDocX.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePatientDocX.json");
    private static final Path DUPLICATE_DOCTOR_FILE = TEST_DATA_FOLDER.resolve("duplicateDoctorDocX.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void toModelType_typicalPatientsFile_success() throws Exception {
        JsonSerializableDocX dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableDocX.class).get();
        DocX docXFromFile = dataFromFile.toModelType();
        DocX typicalPatientsDocX = TypicalPatients.getTypicalDocX();
        //assertEquals(docXFromFile, typicalPatientsDocX);
    }


    @Test
    public void toModelType_invalidPatientFile_throwsIllegalValueException() throws Exception {
        JsonSerializableDocX dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableDocX.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePatients_throwsIllegalValueException() throws Exception {
        JsonSerializableDocX dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableDocX.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableDocX.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }


    @Test
    public void toModelType_typicalDoctorsFile_success() throws Exception {
        JsonSerializableDocX dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableDocX.class).get();
        DocX docXFromFile = dataFromFile.toModelType();
        DocX typicalDoctorsDocX = TypicalDoctors.getTypicalDocX_doctor();
        //assertEquals(docXFromFile, typicalDoctorsDocX);
    }

    @Test
    public void toModelType_invalidDoctorFile_throwsIllegalValueException() throws Exception {
        JsonSerializableDocX dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableDocX.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateDoctors_throwsIllegalValueException() throws Exception {
        JsonSerializableDocX dataFromFile = JsonUtil.readJsonFile(DUPLICATE_DOCTOR_FILE,
                JsonSerializableDocX.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableDocX.MESSAGE_DUPLICATE_DOCTOR);
        dataFromFile.toModelType();
    }
}
