package seedu.address.model.statistics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.YEARLY_REVENUE1;
import static seedu.address.testutil.TypicalRestOrRant.YEARLY_REVENUE2;

import org.junit.Test;

import seedu.address.testutil.YearlyRevenueBuilder;

public class YearlyRevenueTest {
    @Test
    public void isSameYearlyRevenue() {
        // same object -> returns true
        assertTrue(YEARLY_REVENUE1.isSameYearlyRevenue(YEARLY_REVENUE1));

        // null -> returns false
        assertFalse(YEARLY_REVENUE1.isSameYearlyRevenue(null));

        // different year -> returns false
        YearlyRevenue editedYearlyRevenue = new YearlyRevenueBuilder(YEARLY_REVENUE1).withYear("2010").build();
        assertFalse(YEARLY_REVENUE1.isSameYearlyRevenue(editedYearlyRevenue));

        // same year, different totalYearlyRevenue -> returns true
        editedYearlyRevenue = new YearlyRevenueBuilder(YEARLY_REVENUE1).withTotalYearlyRevenue("300").build();
        assertTrue(YEARLY_REVENUE1.isSameYearlyRevenue(editedYearlyRevenue));
    }

    @Test
    public void equals() {
        // same values -> returns true
        YearlyRevenue yearlyRevenueCopy = new YearlyRevenueBuilder(YEARLY_REVENUE1).build();
        assertTrue(YEARLY_REVENUE1.equals(yearlyRevenueCopy));

        // same object -> returns true
        assertTrue(YEARLY_REVENUE1.equals(YEARLY_REVENUE1));

        // null -> returns false
        assertFalse(YEARLY_REVENUE1.equals(null));

        // different type -> returns false
        assertFalse(YEARLY_REVENUE1.equals(5));

        // different daily revenue -> returns false
        assertFalse(YEARLY_REVENUE1.equals(YEARLY_REVENUE2));

        // different year -> returns false
        YearlyRevenue editedYearlyRevenue = new YearlyRevenueBuilder(YEARLY_REVENUE1).withYear("2010").build();
        assertFalse(YEARLY_REVENUE1.equals(editedYearlyRevenue));

        // same year, different totalYearlyRevenue -> returns false
        editedYearlyRevenue = new YearlyRevenueBuilder(YEARLY_REVENUE1).withTotalYearlyRevenue("300").build();
        assertFalse(YEARLY_REVENUE1.equals(editedYearlyRevenue));
    }
}
