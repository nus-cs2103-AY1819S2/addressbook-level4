package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE1;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE2;

import org.junit.Test;

import seedu.address.testutil.StatisticsBuilder;

public class DailyRevenueTest {
    @Test
    public void isSameOrderItem() {
        // same object -> returns true
        assertTrue(DAILY_REVENUE1.isSameDailyRevenue(DAILY_REVENUE1));

        // null -> returns false
        assertFalse(DAILY_REVENUE1.isSameDailyRevenue(null));

        // different day -> returns false
        DailyRevenue editedDailyRevenue = new StatisticsBuilder(DAILY_REVENUE1).withDay("10").build();
        assertFalse(DAILY_REVENUE1.isSameDailyRevenue(editedDailyRevenue));

        // different month -> returns false
        editedDailyRevenue = new StatisticsBuilder(DAILY_REVENUE1).withMonth("04").build();
        assertFalse(DAILY_REVENUE1.isSameDailyRevenue(editedDailyRevenue));

        // different year -> returns false
        editedDailyRevenue = new StatisticsBuilder(DAILY_REVENUE1).withYear("2010").build();
        assertFalse(DAILY_REVENUE1.isSameDailyRevenue(editedDailyRevenue));

        // same day, month, year, different totalDailyRevenue -> returns true
        editedDailyRevenue = new StatisticsBuilder(DAILY_REVENUE1).withTotalDailyRevenue("300").build();
        assertTrue(DAILY_REVENUE1.isSameDailyRevenue(editedDailyRevenue));
    }

    @Test
    public void equals() {
        // same values -> returns true
        DailyRevenue orderItemCopy = new StatisticsBuilder(DAILY_REVENUE1).build();
        assertTrue(DAILY_REVENUE1.equals(orderItemCopy));

        // same object -> returns true
        assertTrue(DAILY_REVENUE1.equals(DAILY_REVENUE1));

        // null -> returns false
        assertFalse(DAILY_REVENUE1.equals(null));

        // different type -> returns false
        assertFalse(DAILY_REVENUE1.equals(5));

        // different daily revenue -> returns false
        assertFalse(DAILY_REVENUE1.equals(DAILY_REVENUE2));

        // different day -> returns false
        DailyRevenue editedDailyRevenue = new StatisticsBuilder(DAILY_REVENUE1).withDay("10").build();
        assertFalse(DAILY_REVENUE1.equals(editedDailyRevenue));

        // different month -> returns false
        editedDailyRevenue = new StatisticsBuilder(DAILY_REVENUE1).withMonth("04").build();
        assertFalse(DAILY_REVENUE1.equals(editedDailyRevenue));

        // different year -> returns false
        editedDailyRevenue = new StatisticsBuilder(DAILY_REVENUE1).withYear("2010").build();
        assertFalse(DAILY_REVENUE1.equals(editedDailyRevenue));

        // same day, month, year, different totalDailyRevenue -> returns false
        editedDailyRevenue = new StatisticsBuilder(DAILY_REVENUE1).withTotalDailyRevenue("300").build();
        assertFalse(DAILY_REVENUE1.equals(editedDailyRevenue));
    }
}
