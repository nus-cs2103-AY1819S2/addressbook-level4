package seedu.finance.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.finance.commons.exceptions.IllegalValueException;
import seedu.finance.commons.util.JsonUtil;
import seedu.finance.model.FinanceTracker;
import seedu.finance.model.record.Description;
import seedu.finance.model.record.Record;
import seedu.finance.testutil.RecordBuilder;
import seedu.finance.testutil.TypicalRecords;

public class JsonSerializableFinanceTrackerTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableFinanceTrackerTest");
    private static final Path TYPICAL_RECORDS_FILE = TEST_DATA_FOLDER.resolve("typicalRecordsFinanceTracker.json");
    private static final Path INVALID_RECORD_FILE = TEST_DATA_FOLDER.resolve("invalidRecordFinanceTracker.json");
    private static final Path DUPLICATE_RECORD_FILE = TEST_DATA_FOLDER.resolve("duplicateRecordFinanceTracker.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalRecordsFile_success() throws Exception {
        JsonSerializableFinanceTracker dataFromFile = JsonUtil.readJsonFile(TYPICAL_RECORDS_FILE,
                JsonSerializableFinanceTracker.class).get();
        FinanceTracker financeTrackerFromFile = dataFromFile.toModelType();
        FinanceTracker typicalRecordsFinanceTracker = TypicalRecords.getTypicalFinanceTracker();
        assertEquals(financeTrackerFromFile, typicalRecordsFinanceTracker);
    }

    @Test
    public void toModelType_duplicateRecords_success() throws Exception {
        JsonSerializableFinanceTracker dataFromFile = JsonUtil.readJsonFile(DUPLICATE_RECORD_FILE,
                JsonSerializableFinanceTracker.class).get();
        FinanceTracker financeTrackerFromFile = dataFromFile.toModelType();
        Record record = new RecordBuilder().withName("Fries").withAmount("3.00").withDate("03/02/2019")
                .withDescription(new Description("some description")).withCategory("friends").build();
        FinanceTracker financeTracker = new FinanceTracker();
        financeTracker.addRecord(record);
        financeTracker.addRecord(record);
        assertEquals(financeTrackerFromFile, financeTracker);
    }

    @Test
    public void toModelType_invalidRecordFile_throwsIllegalValueException() throws Exception {
        JsonSerializableFinanceTracker dataFromFile = JsonUtil.readJsonFile(INVALID_RECORD_FILE,
                JsonSerializableFinanceTracker.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

}
