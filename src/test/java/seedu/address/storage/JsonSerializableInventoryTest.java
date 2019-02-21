package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Inventory;
import seedu.address.testutil.TypicalMedicines;

public class JsonSerializableInventoryTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableInventoryTest");
    private static final Path TYPICAL_MEDICINES_FILE = TEST_DATA_FOLDER.resolve("typicalMedicinesInventory.json");
    private static final Path INVALID_MEDICINE_FILE = TEST_DATA_FOLDER.resolve("invalidMedicineInventory.json");
    private static final Path DUPLICATE_MEDICINE_FILE = TEST_DATA_FOLDER.resolve("duplicateMedicineInventory.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalMedicinesFile_success() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(TYPICAL_MEDICINES_FILE,
                JsonSerializableInventory.class).get();
        Inventory inventoryFromFile = dataFromFile.toModelType();
        Inventory typicalMedicinesInventory = TypicalMedicines.getTypicalInventory();
        assertEquals(inventoryFromFile, typicalMedicinesInventory);
    }

    @Test
    public void toModelType_invalidMedicineFile_throwsIllegalValueException() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(INVALID_MEDICINE_FILE,
                JsonSerializableInventory.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateMedicines_throwsIllegalValueException() throws Exception {
        JsonSerializableInventory dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MEDICINE_FILE,
                JsonSerializableInventory.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableInventory.MESSAGE_DUPLICATE_MEDICINE);
        dataFromFile.toModelType();
    }

}
