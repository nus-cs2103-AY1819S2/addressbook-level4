package seedu.hms.model;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.hms.commons.core.LogsCenter;
import seedu.hms.model.booking.Booking;
import seedu.hms.model.booking.serviceType.ServiceType;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.reservation.Reservation;
import seedu.hms.model.reservation.roomType.RoomType;

/**
 * Provide stats for a specific hms
 */
public class Stats {
    private static final Logger logger = LogsCenter.getLogger(Stats.class);
    private final ReadOnlyHotelManagementSystem hms;

    public Stats(ReadOnlyHotelManagementSystem hms) {
        this.hms = hms;
    }

    public Map<String, Long> countServiceTypes() {
        return sortAndFormat(
                count(hms.getBookingList(), (b) -> b.getService().getName()));
    }

    public Map<String, Long> countRoomTypes() {
        return sortAndFormat(count(hms.getReservationList(), (r) -> r.getRoom().getName()));
    }

    public Map<String, Long> countPayerForServices() {
        return count(hms.getBookingList(),
                b -> (b.getPayer().getName().toString() + b.getPayer().getIdNum().toString()));
    }

    public Map<String, Long> countPayerForReservations() {
        return count(hms.getReservationList(),
                r -> (r.getPayer().getName().toString() + r.getPayer().getIdNum().toString()));
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

    // utils
    private <E, T> Map<T, Long> count(ObservableList<E> ol, Function<E, T> f) {
        return ol.stream()
                .collect(Collectors.groupingBy(f, Collectors.counting()));
    }

    /**
     * Sorts a map by its value.
     * @param m The map to be sorted
     * @param isDesc Whether the result should be descending or ascending
     * @param f The formatting function
     * @return The sorted map
     */
    private Map<String, Long> sortAndFormat(Map<String, Long> m, boolean isDesc, Function<String, String> f) {
        return m.entrySet().stream().sorted((
                    isDesc
                    ? Collections.reverseOrder(Map.Entry.comparingByValue())
                    : Map.Entry.comparingByValue()))
                .collect(Collectors.toMap((e) -> f.apply(e.getKey()), Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    private Map<String, Long> sortAndFormat(Map<String, Long> m) {
        return sortAndFormat(m, true, s -> s);
    }

    private Map<String, Long> sortAndFormat(Map<String, Long> m, boolean isDesc) {
        return sortAndFormat(m, isDesc, s -> s);
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

    public ReadOnlyHotelManagementSystem getHms() {
        return hms;
    }
}
