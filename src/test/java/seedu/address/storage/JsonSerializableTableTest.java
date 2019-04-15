package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalTables;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.table.Table;
import seedu.address.model.table.Tables;

public class JsonSerializableTableTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableTableTest");
    private static final Path TYPICAL_TABLE_FILE = TEST_DATA_FOLDER.resolve("typicalTables.json");
    private static final Path INVALID_TABLE_FILE = TEST_DATA_FOLDER.resolve("invalidTables.json");
    private static final Path DUPLICATE_TABLE_FILE = TEST_DATA_FOLDER.resolve("duplicateTables.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalTableFile_success() throws Exception {
        JsonSerializableTables dataFromFile = JsonUtil.readJsonFile(TYPICAL_TABLE_FILE,
                JsonSerializableTables.class).get();
        Tables tablesFromFile = dataFromFile.toModelType();
        Tables typicalTableTables = new Tables();
        for (Table table : getTypicalTables()) {
            typicalTableTables.addTable(table);
        }

        assertEquals(tablesFromFile, typicalTableTables);
    }

    @Test
    public void toModelType_invalidTableFile_throwsIllegalValueException() throws Exception {
        JsonSerializableTables dataFromFile = JsonUtil.readJsonFile(INVALID_TABLE_FILE,
                JsonSerializableTables.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateTable_throwsIllegalValueException() throws Exception {
        JsonSerializableTables dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TABLE_FILE,
                JsonSerializableTables.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableTables.MESSAGE_DUPLICATE_TABLE);
        dataFromFile.toModelType();
    }

}
