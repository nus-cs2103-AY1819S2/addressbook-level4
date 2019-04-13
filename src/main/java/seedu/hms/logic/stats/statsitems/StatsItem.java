package seedu.hms.logic.stats.statsitems;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.hms.logic.stats.Stats;

public abstract class StatsItem {
    protected String title;
    protected final Stats stats;

    public StatsItem(Stats s) {
        this.stats = s;
    }

    public String getTitle() {
        return (this.title != null ? this.title : "");
    }

    public abstract Map<String, Long> calcResult(boolean isDesc);
    public Map<String, Long> calcResult() {
        return calcResult(true);
    }

    // utils
    protected <E, T> Map<T, Long> count(ObservableList<E> ol, Function<E, T> f) {
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
    protected Map<String, Long> sortAndFormat(Map<String, Long> m, boolean isDesc, Function<String, String> f) {
        return m.entrySet().stream().sorted((
                isDesc
                        ? Collections.reverseOrder(Map.Entry.comparingByValue())
                        : Map.Entry.comparingByValue()))
                .collect(Collectors.toMap((e) -> f.apply(e.getKey()), Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    protected Map<String, Long> sortAndFormat(Map<String, Long> m) {
        return sortAndFormat(m, true, s -> s);
    }

    protected Map<String, Long> sortAndFormat(Map<String, Long> m, boolean isDesc) {
        return sortAndFormat(m, isDesc, s -> s);
    }
}
