package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.GradTrak;
import seedu.address.testutil.TypicalModuleTaken;

public class JsonSerializableGradTrakTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableGradTrakTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalModulesTaken.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidModulesTaken.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicateModulesTaken.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableGradTrak dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableGradTrak.class).get();
        GradTrak addressBookFromFile = dataFromFile.toModelType();
        GradTrak typicalPersonsAddressBook = TypicalModuleTaken.getTypicalGradTrak();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableGradTrak dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableGradTrak.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableGradTrak dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableGradTrak.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableGradTrak.MESSAGE_DUPLICATE_MODULES_TAKEN);
        dataFromFile.toModelType();
    }

}
