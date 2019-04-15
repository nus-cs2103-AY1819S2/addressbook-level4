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

public class JsonSerializableEquipmentManagerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableEquipmentManagerTest");
    private static final Path TYPICAL_EQUIPMENT_FILE = TEST_DATA_FOLDER.resolve("typicalEquipmentEM.json");
    private static final Path INVALID_EQUIPMENT_FILE = TEST_DATA_FOLDER.resolve("invalidEquipmentEM.json");
    private static final Path DUPLICATE_EQUIPMENT_FILE = TEST_DATA_FOLDER.resolve("duplicateEquipmentEM.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalEquipmentFile_success() throws Exception {
        JsonSerializableEquipmentManager dataFromFile = JsonUtil.readJsonFile(TYPICAL_EQUIPMENT_FILE,
                JsonSerializableEquipmentManager.class).get();

        EquipmentManager equipmentManagerFromFile = dataFromFile.toModelType();
        EquipmentManager typicalPersonsEquipmentManager = TypicalEquipments.getTypicalEquipmentManager();
        assertEquals(equipmentManagerFromFile, typicalPersonsEquipmentManager);
    }

    @Test
    public void toModelType_invalidEquipmentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableEquipmentManager dataFromFile = JsonUtil.readJsonFile(INVALID_EQUIPMENT_FILE,
                JsonSerializableEquipmentManager.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateEquipment_throwsIllegalValueException() throws Exception {
        JsonSerializableEquipmentManager dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EQUIPMENT_FILE,
                JsonSerializableEquipmentManager.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableEquipmentManager.MESSAGE_DUPLICATE_EQUIPMENT);
        dataFromFile.toModelType();
    }

}
