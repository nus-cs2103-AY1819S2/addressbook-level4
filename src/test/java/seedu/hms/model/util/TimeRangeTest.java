package seedu.hms.model.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeRangeTest {
    @Test
    public void withinTiming() {
        TimeRange serviceTiming = new TimeRange(10, 20);
        assertTrue(new TimeRange(11, 19).withinTiming(serviceTiming));
        assertFalse(new TimeRange(9, 21).withinTiming(serviceTiming));
        assertFalse(new TimeRange(11, 21).withinTiming(serviceTiming));
        assertFalse(new TimeRange(9, 19).withinTiming(serviceTiming));
        assertTrue(new TimeRange(10, 20).withinTiming(serviceTiming));
        assertTrue(serviceTiming.withinTiming(serviceTiming));
    }

    @Test
    public void numOfHours() {
        assertTrue(new TimeRange(11, 19).numOfHours() == 8);
        assertTrue(new TimeRange(9, 21).numOfHours() == 12);
        assertTrue(new TimeRange(12, 23).numOfHours() == 11);
        assertTrue(new TimeRange(0, 12).numOfHours() == 12);
        assertTrue(new TimeRange(0, 23).numOfHours() == 23);
    }

    @Test
    public void getHourlySlots() {
        assertTrue(new TimeRange(10, 19).getHourlySlots().equals(
                new TimeRange(10, 19).getHourlySlots()));
    }

    @Test
    public void equals() {
        TimeRange timeRange = new TimeRange(13, 18);
        assertTrue(timeRange.equals(timeRange));
        assertTrue(new TimeRange(0, 23).equals(new TimeRange(0, 23)));
        assertTrue(new TimeRange(0, 23).hashCode() == new TimeRange(0, 23).hashCode());
        assertTrue(new TimeRange(0, 23).toString().equals(new TimeRange(0, 23).toString()));
        assertFalse(new TimeRange(0, 23).equals(new TimeRange(0, 22)));
        assertFalse(new TimeRange(0, 23).equals(new TimeRange(5, 23)));
        assertFalse(new TimeRange(0, 23).equals(new TimeRange(5, 13)));
    }
}
