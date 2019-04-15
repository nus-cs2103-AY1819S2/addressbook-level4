package seedu.address.model.statistics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalRestOrRant.DAILY_REVENUE1;
import static seedu.address.testutil.TypicalRestOrRant.getTypicalRevenue;

import java.util.Collection;
import java.util.Collections;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.testutil.StatisticsBuilder;

public class StatisticsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Statistics statistics = new Statistics();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), statistics.getRevenueList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        statistics.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyStatistics_replacesData() {
        Statistics newData = new Statistics();
        for (Revenue revenue : getTypicalRevenue()) {
            newData.addRevenue(revenue);
        }
        statistics.resetData(newData);
        assertEquals(newData, statistics);
    }

    @Test
    public void hasDailyRevenue_nullDailyRevenue_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        statistics.hasRevenue(null);
    }

    @Test
    public void hasDailyRevenue_dailyRevenueNotInStatistics_returnsFalse() {
        assertFalse(statistics.hasRevenue(DAILY_REVENUE1));
    }

    @Test
    public void hasDailyRevenue_dailyRevenueInStatistics_returnsTrue() {
        statistics.addRevenue(DAILY_REVENUE1);
        assertTrue(statistics.hasRevenue(DAILY_REVENUE1));
    }

    @Test
    public void hasDailyRevenue_dailyRevenueWithSameIdentityFieldsInStatistics_returnsTrue() {
        statistics.addRevenue(DAILY_REVENUE1);
        Revenue editedRevenue = new StatisticsBuilder(DAILY_REVENUE1).withTotalRevenue("300").build();
        assertTrue(statistics.hasRevenue(editedRevenue));
    }

    @Test
    public void getDailyRevenueList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        statistics.getRevenueList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        statistics.addListener(listener);
        statistics.addRevenue(DAILY_REVENUE1);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        statistics.addListener(listener);
        statistics.removeListener(listener);
        statistics.addRevenue(DAILY_REVENUE1);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyStatistics whose daily revenue list can violate interface constraints.
     */
    private static class StatisticsStub implements ReadOnlyStatistics {
        private final ObservableList<Revenue> revenues = FXCollections.observableArrayList();

        StatisticsStub(Collection<Revenue> revenues) {
            this.revenues.setAll(revenues);
        }

        @Override
        public ObservableList<Revenue> getRevenueList() {
            return revenues;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }
}
