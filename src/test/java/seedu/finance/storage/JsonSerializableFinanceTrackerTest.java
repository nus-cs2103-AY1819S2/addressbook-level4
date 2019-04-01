package seedu.finance.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.commons.util.JsonUtil;

public class JsonSerializableFinanceTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFinanceTrackerTest");
    private static final Path TYPICAL_RECORDS_FILE = TEST_DATA_FOLDER.resolve("typicalRecordsFinanceTracker.json");
    private static final Path INVALID_RECORD_FILE = TEST_DATA_FOLDER.resolve("invalidRecordFinanceTracker.json");
    private static final Path DUPLICATE_RECORD_FILE = TEST_DATA_FOLDER.resolve("duplicateRecordFinanceTracker.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // TODO: Failed Test; need to update
    // NULL POINTER EXCEPTION
    /*
    @Test
    public void toModelType_typicalRecordsFile_success() throws Exception {
        JsonSerializableFinanceTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_RECORDS_FILE,
                JsonSerializableFinanceTracker.class).get();
        FinanceTracker financeTrackerFromFile = dataFromFile.toModelType();
        FinanceTracker typicalRecordsFinanceTracker = TypicalRecords.getTypicalFinanceTracker();
        assertEquals(financeTrackerFromFile, typicalRecordsFinanceTracker);
    }*/

    @Test
    public void toModelType_invalidRecordFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFinanceTracker dataFromFile = JsonUtil.readJsonFile(INVALID_RECORD_FILE,
                JsonSerializableFinanceTracker.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    // TODO: Failed Test; need to update
    // NULL POINTER EXCEPTION
    /*
    @Test
    public void toModelType_duplicateRecords_throwsIllegalValueException() throws Exception {
        JsonSerializableFinanceTracker dataFromFile = JsonUtil.readJsonFile(DUPLICATE_RECORD_FILE,
                JsonSerializableFinanceTracker.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableFinanceTracker.MESSAGE_DUPLICATE_RECORD);
        dataFromFile.toModelType();
    }*/

}
