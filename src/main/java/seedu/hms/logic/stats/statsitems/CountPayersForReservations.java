package seedu.hms.logic.stats.statsitems;

import java.util.Map;

import seedu.hms.logic.stats.Stats;

/**
 * Count the most frequent payers for reservations.
 */
public class CountPayersForReservations extends StatsItem {
    public CountPayersForReservations(Stats s) {
        super(s);
        this.title = "Frequent Payers for Reservations";
    }

    @Override
    public Map<String, Long> calcResult(boolean isDesc) {
        return updateLongest(sortAndFormat(
                count(
                    stats.getHms().getReservationList(),
                    r -> (r.getPayer().getName().toString() + " (" + r.getPayer().getIdNum().toString() + ")")),
                isDesc));
    }
}
