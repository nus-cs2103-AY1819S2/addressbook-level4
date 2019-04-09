package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE1;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE2;

import org.junit.Test;

import seedu.address.testutil.StatisticsBuilder;

public class RevenueTest {
    @Test
    public void isSameRevenue() {
        // same object -> returns true
        assertTrue(DAILY_REVENUE1.isSameRevenue(DAILY_REVENUE1));

        // null -> returns false
        assertFalse(DAILY_REVENUE1.isSameRevenue(null));

        // different day -> returns false
        Revenue editedRevenue = new StatisticsBuilder(DAILY_REVENUE1).withDay("10").build();
        assertFalse(DAILY_REVENUE1.isSameRevenue(editedRevenue));

        // different month -> returns false
        editedRevenue = new StatisticsBuilder(DAILY_REVENUE1).withMonth("04").build();
        assertFalse(DAILY_REVENUE1.isSameRevenue(editedRevenue));

        // different year -> returns false
        editedRevenue = new StatisticsBuilder(DAILY_REVENUE1).withYear("2010").build();
        assertFalse(DAILY_REVENUE1.isSameRevenue(editedRevenue));

        // same day, month, year, different totalDailyRevenue -> returns true
        editedRevenue = new StatisticsBuilder(DAILY_REVENUE1).withTotalRevenue("300").build();
        assertTrue(DAILY_REVENUE1.isSameRevenue(editedRevenue));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Revenue revenueCopy = new StatisticsBuilder(DAILY_REVENUE1).build();
        assertTrue(DAILY_REVENUE1.equals(revenueCopy));

        // same object -> returns true
        assertTrue(DAILY_REVENUE1.equals(DAILY_REVENUE1));

        // null -> returns false
        assertFalse(DAILY_REVENUE1.equals(null));

        // different type -> returns false
        assertFalse(DAILY_REVENUE1.equals(5));

        // different daily revenue -> returns false
        assertFalse(DAILY_REVENUE1.equals(DAILY_REVENUE2));

        // different day -> returns false
        Revenue editedRevenue = new StatisticsBuilder(DAILY_REVENUE1).withDay("10").build();
        assertFalse(DAILY_REVENUE1.equals(editedRevenue));

        // different month -> returns false
        editedRevenue = new StatisticsBuilder(DAILY_REVENUE1).withMonth("04").build();
        assertFalse(DAILY_REVENUE1.equals(editedRevenue));

        // different year -> returns false
        editedRevenue = new StatisticsBuilder(DAILY_REVENUE1).withYear("2010").build();
        assertFalse(DAILY_REVENUE1.equals(editedRevenue));

        // same day, month, year, different totalDailyRevenue -> returns false
        editedRevenue = new StatisticsBuilder(DAILY_REVENUE1).withTotalRevenue("300").build();
        assertFalse(DAILY_REVENUE1.equals(editedRevenue));
    }
}
