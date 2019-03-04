package seedu.address.model.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TimeRangeTest {

    @Test
    public void withinTiming() {
        TimeRange serviceTiming = new TimeRange(10, 20);
        assertTrue(serviceTiming.withinTiming(new TimeRange(11, 19)));
        assertFalse(serviceTiming.withinTiming(new TimeRange(9, 21)));
        assertFalse(serviceTiming.withinTiming(new TimeRange(11, 21)));
        assertFalse(serviceTiming.withinTiming(new TimeRange(9, 19)));
        assertTrue(serviceTiming.withinTiming(new TimeRange(10, 20)));
        assertTrue(serviceTiming.withinTiming(serviceTiming));
    }
}
