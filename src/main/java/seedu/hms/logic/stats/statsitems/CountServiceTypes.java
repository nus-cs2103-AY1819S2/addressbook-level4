package seedu.hms.logic.stats.statsitems;

import java.util.Map;

import seedu.hms.logic.stats.Stats;

/**
 * Count the most popular service types.
 */
public class CountServiceTypes extends StatsItem {
    public CountServiceTypes(Stats s) {
        super(s);
        this.title = "Popular Service Types";
    }

    @Override
    public Map<String, Long> calcResult(boolean isDesc) {
        return updateLongest(sortAndFormat(
                count(
                    stats.getHms().getBookingList(),
                    b -> b.getService().getName()),
                isDesc));
    }
}
