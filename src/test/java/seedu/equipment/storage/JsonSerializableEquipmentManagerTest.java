package seedu.equipment.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.equipment.commons.exceptions.IllegalValueException;
import seedu.equipment.commons.util.JsonUtil;
import seedu.equipment.model.EquipmentManager;
import seedu.equipment.testutil.TypicalEquipments;
import seedu.equipment.storage.JsonSerializableEquipmentManager;

public class JsonSerializableEquipmentManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableEquipmentManagerTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableEquipmentManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableEquipmentManager.class).get();

        EquipmentManager equipmentManagerFromFile = dataFromFile.toModelType();
        EquipmentManager typicalPersonsEquipmentManager = TypicalEquipments.getTypicalAddressBook();
        assertEquals(equipmentManagerFromFile, typicalPersonsEquipmentManager);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEquipmentManager dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableEquipmentManager.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableEquipmentManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableEquipmentManager.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableEquipmentManager.MESSAGE_DUPLICATE_PERSON);
        dataFromFile.toModelType();
    }

}
