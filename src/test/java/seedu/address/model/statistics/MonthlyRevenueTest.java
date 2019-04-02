package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.MONTHLY_REVENUE1;
import static seedu.address.testutil.TypicalRestOrRant.MONTHLY_REVENUE2;

import org.junit.Test;

import seedu.address.testutil.MonthlyRevenueBuilder;

public class MonthlyRevenueTest {
    @Test
    public void isSameMonthlyRevenue() {
        // same object -> returns true
        assertTrue(MONTHLY_REVENUE1.isSameMonthlyRevenue(MONTHLY_REVENUE1));

        // null -> returns false
        assertFalse(MONTHLY_REVENUE1.isSameMonthlyRevenue(null));

        // different month -> returns false
        MonthlyRevenue editedMonthlyRevenue = new MonthlyRevenueBuilder(MONTHLY_REVENUE1).withMonth("04").build();
        assertFalse(MONTHLY_REVENUE1.isSameMonthlyRevenue(editedMonthlyRevenue));

        // different year -> returns false
        editedMonthlyRevenue = new MonthlyRevenueBuilder(MONTHLY_REVENUE1).withYear("2010").build();
        assertFalse(MONTHLY_REVENUE1.isSameMonthlyRevenue(editedMonthlyRevenue));

        // same month, year, different totalMonthlyRevenue -> returns true
        editedMonthlyRevenue = new MonthlyRevenueBuilder(MONTHLY_REVENUE1).withTotalMonthlyRevenue("300").build();
        assertTrue(MONTHLY_REVENUE1.isSameMonthlyRevenue(editedMonthlyRevenue));
    }

    @Test
    public void equals() {
        // same values -> returns true
        MonthlyRevenue monthlyRevenueCopy = new MonthlyRevenueBuilder(MONTHLY_REVENUE1).build();
        assertTrue(MONTHLY_REVENUE1.equals(monthlyRevenueCopy));

        // same object -> returns true
        assertTrue(MONTHLY_REVENUE1.equals(MONTHLY_REVENUE1));

        // null -> returns false
        assertFalse(MONTHLY_REVENUE1.equals(null));

        // different type -> returns false
        assertFalse(MONTHLY_REVENUE1.equals(5));

        // different monthly revenue -> returns false
        assertFalse(MONTHLY_REVENUE1.equals(MONTHLY_REVENUE2));

        // different month -> returns false
        MonthlyRevenue editedMonthlyRevenue = new MonthlyRevenueBuilder(MONTHLY_REVENUE1).withMonth("04").build();
        assertFalse(MONTHLY_REVENUE1.equals(editedMonthlyRevenue));

        // different year -> returns false
        editedMonthlyRevenue = new MonthlyRevenueBuilder(MONTHLY_REVENUE1).withYear("2010").build();
        assertFalse(MONTHLY_REVENUE1.equals(editedMonthlyRevenue));

        // same month, year, different totalMonthlyRevenue -> returns false
        editedMonthlyRevenue = new MonthlyRevenueBuilder(MONTHLY_REVENUE1).withTotalMonthlyRevenue("300").build();
        assertFalse(MONTHLY_REVENUE1.equals(editedMonthlyRevenue));
    }
}
