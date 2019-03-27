package seedu.address.logic.commands;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import seedu.address.model.tag.Condition;
import seedu.address.testutil.Assert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StatisticsTest {

    @Test
    public void nullConditionPassed_getConditionStatistics() {
        Assert.assertThrows(NullPointerException.class, () -> Statistics.getConditionStatistics(null));
    }

    @Test
    public void nullConditionSetPassed_updateStatistics() {
        Assert.assertThrows(NullPointerException.class, () -> Statistics.updateStatistics(null));
    }

    @Test
    public void ifConditionUpdated_getConditionStatistics() {
        Statistics.clearStatistics();
        Condition firstToAdd = new Condition("AIDS");
        Condition secondToAdd = new Condition("Cancer");
        Set<Condition> conditionSetToAdd = new HashSet<>(Arrays.asList(firstToAdd, secondToAdd));
        Statistics.updateStatistics(conditionSetToAdd);
        Condition thirdToAdd = new Condition("AIDS");
        conditionSetToAdd = new HashSet<>(Arrays.asList(thirdToAdd));
        Statistics.updateStatistics(conditionSetToAdd);
        assertEquals(Statistics.getConditionStatistics(firstToAdd), (Integer) 2);
        assertEquals(Statistics.getConditionStatistics(secondToAdd), (Integer) 1);
    }
}
