package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedDailyRevenue.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE1;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Year;
import seedu.address.testutil.Assert;

public class JsonAdaptedDailyRevenueTest {

    private static final String INVALID_DAY = "@!";
    private static final String INVALID_MONTH = "^.";
    private static final String INVALID_YEAR = "2020";

    private static final String VALID_DAY = DAILY_REVENUE1.getDay().toString();
    private static final String VALID_MONTH = DAILY_REVENUE1.getMonth().toString();
    private static final String VALID_YEAR = DAILY_REVENUE1.getYear().toString();
    private static final String VALID_TOTAL_DAILY_REVENUE = Float.toString(DAILY_REVENUE1.getTotalDailyRevenue());

    @Test
    public void toModelType_validDailyRevenueDetails_returnsDailyRevenue() throws Exception {
        JsonAdaptedDailyRevenue dailyRevenue = new JsonAdaptedDailyRevenue(DAILY_REVENUE1);
        assertEquals(DAILY_REVENUE1, dailyRevenue.toModelType());
    }

    @Test
    public void toModelType_invalidDay_throwsIllegalValueException() {
        JsonAdaptedDailyRevenue dailyRevenue = new JsonAdaptedDailyRevenue(INVALID_DAY, VALID_MONTH, VALID_YEAR,
                VALID_TOTAL_DAILY_REVENUE);
        String expectedMessage = Day.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, dailyRevenue::toModelType);
    }

    @Test
    public void toModelType_nullDay_throwsIllegalValueException() {
        JsonAdaptedDailyRevenue dailyRevenue = new JsonAdaptedDailyRevenue(null, VALID_MONTH, VALID_YEAR,
                VALID_TOTAL_DAILY_REVENUE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, dailyRevenue::toModelType);
    }

    @Test
    public void toModelType_invalidMonth_throwsIllegalValueException() {
        JsonAdaptedDailyRevenue dailyRevenue = new JsonAdaptedDailyRevenue(VALID_DAY, INVALID_MONTH, VALID_YEAR,
                VALID_TOTAL_DAILY_REVENUE);
        String expectedMessage = Month.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, dailyRevenue::toModelType);
    }

    @Test
    public void toModelType_nullMonth_throwsIllegalValueException() {
        JsonAdaptedDailyRevenue dailyRevenue = new JsonAdaptedDailyRevenue(VALID_DAY, null, VALID_YEAR,
                VALID_TOTAL_DAILY_REVENUE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Month.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, dailyRevenue::toModelType);
    }

    @Test
    public void toModelType_invalidYear_throwsIllegalValueException() {
        JsonAdaptedDailyRevenue dailyRevenue = new JsonAdaptedDailyRevenue(VALID_DAY, VALID_MONTH, INVALID_YEAR,
                VALID_TOTAL_DAILY_REVENUE);
        String expectedMessage = Year.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, dailyRevenue::toModelType);
    }

    @Test
    public void toModelType_nullYear_throwsIllegalValueException() {
        JsonAdaptedDailyRevenue dailyRevenue = new JsonAdaptedDailyRevenue(VALID_DAY, VALID_MONTH, null,
                VALID_TOTAL_DAILY_REVENUE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Year.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, dailyRevenue::toModelType);
    }

}
