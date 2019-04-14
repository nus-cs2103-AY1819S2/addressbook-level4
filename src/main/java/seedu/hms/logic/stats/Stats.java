package seedu.hms.logic.stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import seedu.hms.commons.core.LogsCenter;
import seedu.hms.logic.stats.statsitems.CountPayersForReservations;
import seedu.hms.logic.stats.statsitems.CountPayersForServices;
import seedu.hms.logic.stats.statsitems.CountRoomTypes;
import seedu.hms.logic.stats.statsitems.CountServiceTypes;
import seedu.hms.logic.stats.statsitems.StatsItem;
import seedu.hms.model.ReadOnlyHotelManagementSystem;

/**
 * Provide stats for a specific hms
 */
public class Stats {
    private static final Logger logger = LogsCenter.getLogger(Stats.class);
    private final ReadOnlyHotelManagementSystem hms;
    private final List<StatsItem> statsitems;

    public Stats(ReadOnlyHotelManagementSystem hms) {
        this.hms = hms;
        this.statsitems = new ArrayList<>(Arrays.asList(
                new CountRoomTypes(this),
                new CountServiceTypes(this),
                new CountPayersForReservations(this),
                new CountPayersForServices(this)
        ));
    }

    private static String fillOnLeft(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    /**
     * Generate a text report for all the StatsItems.
     * @return A string of the text report.
     */
    public String toTextReport() {
        final StringBuilder sb = new StringBuilder();
        for (StatsItem si : statsitems) {
            sb.append("*** " + si.getTitle() + "\n");
            sb.append(si.toTextReport());
            sb.append("\n");
        }
        return sb.toString();
    }

    public ReadOnlyHotelManagementSystem getHms() {
        return hms;
    }
}
