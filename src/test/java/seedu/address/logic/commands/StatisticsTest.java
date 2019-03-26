package seedu.address.logic.commands;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class StatisticsTest {

    @Test
    public void nullConditionPassed_getConditionStatistics() {
        // null condition
        Assert.assertThrows(NullPointerException.class, () -> Statistics.getConditionStatistics(null));
    }

    @Test
    public void nullConditionSetPassed_updateStatistics() {
        Assert.assertThrows(NullPointerException.class, () -> Statistics.updateStatistics(null));
    }
}
