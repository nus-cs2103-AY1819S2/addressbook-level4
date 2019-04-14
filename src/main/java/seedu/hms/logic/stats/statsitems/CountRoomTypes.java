package seedu.hms.logic.stats.statsitems;

import java.util.Map;

import seedu.hms.logic.stats.Stats;

/**
 * Count the most popular room types.
 */
public class CountRoomTypes extends StatsItem {
    public CountRoomTypes(Stats s) {
        super(s);
        this.title = "Popular Room Types";
    }

    @Override
    public Map<String, Long> calcResult(boolean isDesc) {
        return updateLongest(sortAndFormat(
                count(
                    stats.getHms().getReservationList(),
                    r -> (r.getRoom().getName())),
                isDesc));
    }
}
