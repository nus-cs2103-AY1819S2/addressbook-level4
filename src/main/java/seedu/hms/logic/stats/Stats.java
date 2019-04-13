package seedu.hms.logic.stats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.logic.stats.statsitems.CountPayersForReservations;
import seedu.hms.logic.stats.statsitems.CountPayersForServices;
import seedu.hms.logic.stats.statsitems.CountRoomTypes;
import seedu.hms.logic.stats.statsitems.CountServiceTypes;
import seedu.hms.logic.stats.statsitems.StatsItem;
import seedu.hms.model.ReadOnlyHotelManagementSystem;
import seedu.hms.model.booking.serviceType.ServiceType;

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

    public Map<Integer, Long> getPeakHourForService(ServiceType serviceType) {
        // a map containing the number of bookings found per hour
        Map<Integer, Long> hourToBookings = new LinkedHashMap<>(24);
        for (int i = 0; i < 24; i++) {
            hourToBookings.put(i, (long) 0);
        }

        hms.getBookingList().stream().filter(b -> b.getService().equals(serviceType))
                .forEach(b -> {
                    int start = b.getTiming().getStartTime().getHour();
                    int end = b.getTiming().getEndTime().getHour();
                    assert (start < 24) && (end < 24);
                    for (int i = start; i < end; i++) {
                        hourToBookings.put(i, hourToBookings.get(i) + 1);
                    }
                });
        return hourToBookings;
    }

    private static String fillOnLeft(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    // public utils
    public static String prettyMapString(Map<String, Long> m) {
        StringBuilder sb = new StringBuilder();
        Iterator<Entry<String, Long>> iter = m.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, Long> entry = iter.next();
            sb.append(fillOnLeft(entry.getKey(), 15)).append(" --- ").append(entry.getValue());
            sb.append('\n');
        }
        return sb.toString();
    }

    public String toTextReport() {
        final StringBuilder sb = new StringBuilder();
        for (StatsItem si : statsitems) {
            sb.append("*** " + si.getTitle() + "\n");
            sb.append(Stats.prettyMapString(si.calcResult()));
            sb.append("\n");
        }
        return sb.toString();
    }

    public ReadOnlyHotelManagementSystem getHms() {
        return hms;
    }
}
