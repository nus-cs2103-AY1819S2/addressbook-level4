package seedu.address.model.util;

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
}
