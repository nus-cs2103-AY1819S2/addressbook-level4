package seedu.address.logic.commands;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRequestBook;
import seedu.address.model.request.Request;
import seedu.address.model.tag.Condition;

/**
 * Class that represents a TreeMap of conditions and their respective occurences
 */
public class Statistics {

    private static final String MESSAGE_EMPTY_STATISTICS = "Conditions and their related occurences are not available";

    //statistics will be ordering health conditions by key
    private static Map<String, Integer> statistics = new TreeMap<>();

    //sortedMap will be ordering the health conditions by their corresponding number of occurrences
    private static Map<String, Integer> sortedMap = new LinkedHashMap<>();

    /**
     * Returns the number of times the specific condition has appeared in added requests
     *
     * @param condition to check for.
     * @return number of condition occurences in the map
     */
    public static Integer getConditionStatistics(Condition condition) {
        return statistics.getOrDefault(condition.toString().toUpperCase(), 0);
    }

    /**
     * Clears records on both statistics and sortedMap
     */
    public static void clearStatistics() {
        statistics.clear();
        sortedMap.clear();
    }

    /**
     * Updates statistics tree map every time a new request is deleted
     * the integer value pegged to the toBeDeleted Condition will be reduced
     *
     * @param conditionSet to check for.
     */
    public static void deleteStatistics(Set<Condition> conditionSet) {
        for (Condition condition : conditionSet) {
            String conditionName = condition.toString().toUpperCase();
            Integer count = statistics.get(conditionName);
            statistics.put(conditionName, count - 1);
            if (getConditionStatistics(condition) == 0) {
                statistics.remove(conditionName);
            }
        }
        sortStatistics();
    }

    /**
     * Updates statistics tree map every time a new request is added
     * If it is a new condition, a new entry in the tree map will be made
     * Else, the integer value will be incremented
     *
     * @param conditionSet to check for.
     */
    public static void updateStatistics(Set<Condition> conditionSet) {
        for (Condition condition : conditionSet) {
            String conditionName = condition.toString().toUpperCase();
            Integer count = statistics.get(conditionName);
            statistics.put(conditionName, (count == null) ? 1 : count + 1);
        }
        sortStatistics();
    }

    /**
     * Sorts statistics tree map in reverse order of value (Key: condition name,
     * Value: number of occurrences) -> condition with highest incidence rate will
     * appear at the top of the outputted list
     */
    public static void sortStatistics() {
        sortedMap.clear();
        statistics.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
    }

    /**
     * Integrated method with Undo/Redo commands. Takes an ObservableList of all
     * requests and extracts health conditions from each request. updateStatistics is then
     * called to modify statistics to the current state of requests
     */
    public static void undoRedoStatistics(Model model) {
        ReadOnlyRequestBook requestBook = model.getRequestBook();
        ObservableList<Request> requestList = requestBook.getRequestList();
        clearStatistics();
        for (Request request : requestList) {
            Set<Condition> conditionSet = request.getConditions();
            updateStatistics(conditionSet);
        }
        sortStatistics();
    }

    /**
     * Displays all conditions in a standard format
     */
    public static String toCommand() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Conditions and Occurences:\n");
        if (statistics.isEmpty()) {
            return MESSAGE_EMPTY_STATISTICS;
        }
        for (Map.Entry<String, Integer> statistic : sortedMap.entrySet()) {
            stringBuilder.append(statistic.getKey())
                    .append(": ").append(statistic.getValue()).append(" occurences\n");
        }
        return stringBuilder.toString();
    }
}
