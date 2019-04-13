package seedu.hms.logic.stats.statsitems;

import java.util.Map;

import seedu.hms.logic.stats.Stats;

public class CountPayersForReservations extends StatsItem {
    public CountPayersForReservations(Stats s) {
        super(s);
        this.title = "Frequent Payers for Reservations";
    }

    @Override
    public Map<String, Long> calcResult(boolean isDesc) {
        return count(stats.getHms().getReservationList(),
            r -> (r.getPayer().getName().toString() + r.getPayer().getIdNum().toString()));
    }
}
