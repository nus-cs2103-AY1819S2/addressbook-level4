package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.statistics.Statistics;
import seedu.address.testutil.TypicalRestOrRant;

public class JsonSerializableStatisticsTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "JsonSerializableStatisticsTest");
    private static final Path TYPICAL_DAILY_REVENUE_FILE = TEST_DATA_FOLDER.resolve("typicalDailyRevenue.json");
    private static final Path INVALID_DAILY_REVENUE_FILE = TEST_DATA_FOLDER.resolve("invalidDailyRevenue.json");
    private static final Path DUPLICATE_DAILY_REVENUE_FILE = TEST_DATA_FOLDER.resolve("duplicateDailyRevenue.json");
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalDailyRevenueFile_success() throws Exception {
        JsonSerializableStatistics dataFromFile = JsonUtil.readJsonFile(TYPICAL_DAILY_REVENUE_FILE,
                JsonSerializableStatistics.class).get();
        Statistics restOrRantFromFile = dataFromFile.toModelType();
        Statistics typicalDailyRevenues = TypicalRestOrRant.getTypicalRestOrRant().getStatistics();
        assertEquals(restOrRantFromFile, typicalDailyRevenues);
    }

    @Test
    public void toModelType_invalidDailyRevenueFile_throwsIllegalValueException() throws Exception {
        JsonSerializableStatistics dataFromFile = JsonUtil.readJsonFile(INVALID_DAILY_REVENUE_FILE,
                JsonSerializableStatistics.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateMenuItems_throwsIllegalValueException() throws Exception {
        JsonSerializableStatistics dataFromFile = JsonUtil.readJsonFile(DUPLICATE_DAILY_REVENUE_FILE,
                JsonSerializableStatistics.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableStatistics.MESSAGE_DUPLICATE_ITEM);
        dataFromFile.toModelType();
    }
}
