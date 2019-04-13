package seedu.hms.logic.stats.statsitems;

import java.util.Map;

import seedu.hms.logic.stats.Stats;

public class CountPayersForServices extends StatsItem {
    public CountPayersForServices(Stats s) {
        super(s);
        this.title = "Frequent Payers for Services";
    }

    @Override
    public Map<String, Long> calcResult(boolean isDesc) {
        return count(stats.getHms().getBookingList(),
            b -> (b.getPayer().getName().toString() + b.getPayer().getIdNum().toString()));
    }
}
