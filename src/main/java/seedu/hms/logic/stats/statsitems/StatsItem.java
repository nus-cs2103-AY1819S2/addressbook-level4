package seedu.hms.logic.stats.statsitems;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.hms.logic.stats.Stats;

/**
 * An item in the stats.
 */
public abstract class StatsItem {
    protected String title;

    protected int longest;
    protected Map<String, Long> map;
    protected final Stats stats;

    public StatsItem(Stats stats) {
        this.stats = stats;
        this.longest = 15;
        this.map = calcResult();
    }

    public String getTitle() {
        return (this.title != null ? this.title : "");
    }
    public Map<String, Long> getMap() {
        return this.map;
    }

    /**
     * Generate a Category-Quantity map.
     * @param isDesc Whether the map should be in descending order.
     * @return A Category-Quantity map.
     */
    public abstract Map<String, Long> calcResult(boolean isDesc);
    public Map<String, Long> calcResult() {
        return this.calcResult(true);
    }

    /**
     * Update the map.
     */
    public void updateMap() {
        this.map = calcResult();
    }

    /**
     * Generate a text report for this StatsItem
     * @param isDesc Whether the map should be in descending order.
     * @return A string for the text report.
     */
    public String toTextReport(boolean isDesc) {
        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, Long>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Long> entry = iter.next();
            sb.append(fillOnLeft(entry.getKey(), longest) + " --- " + entry.getValue());
            sb.append('\n');
        }
        return sb.toString();
    }
    public String toTextReport() {
        return toTextReport(true);
    }

    /*---- utils ----*/

    private static String fillOnLeft(String s, int n) {
        return String.format("%-" + n + "s", s);
    }

    protected <E> Map<String, Long> count(ObservableList<E> ol, Function<E, String> f) {
        return ol.stream()
                .collect(Collectors.groupingBy(f, Collectors.counting()));
    }

    /**
     * Updates the longest key in the map
     * @param m the map
     * @return the map (nothing changed)
     */
    protected Map<String, Long> updateLongest(Map<String, Long> m) {
        if (!m.isEmpty()) {
            this.longest = m.keySet().stream().max(Comparator.comparingInt(String::length)).get().length();
        }
        return m;
    }

    /**
     * Sorts a map by its value.
     * @param m The map to be sorted
     * @param isDesc Whether the result should be descending or ascending
     * @param f The formatting function
     * @return The sorted map
     */
    protected Map<String, Long> sortAndFormat(Map<String, Long> m, boolean isDesc, Function<String, String> f) {
        return m.entrySet().stream().sorted((
                isDesc
                        ? Collections.reverseOrder(Map.Entry.comparingByValue())
                        : Map.Entry.comparingByValue()))
                .collect(Collectors.toMap((e) -> f.apply(e.getKey()),
                                Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    protected Map<String, Long> sortAndFormat(Map<String, Long> m) {
        return sortAndFormat(m, true, s -> s);
    }

    protected Map<String, Long> sortAndFormat(Map<String, Long> m, boolean isDesc) {
        return sortAndFormat(m, isDesc, s -> s);
    }
}
