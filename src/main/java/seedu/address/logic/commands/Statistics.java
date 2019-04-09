package seedu.address.logic.commands;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import seedu.address.model.tag.Condition;

/**
 * Class that represents a TreeMap of conditions and their respective occurences
 */
public class Statistics {

    private static final String MESSAGE_EMPTY_STATISTICS = "Conditions and their related occurences are not available";

    private static Map<String, Integer> statistics = new TreeMap<>();

    /**
     * Returns the number of times the specific condition has appeared in added requests
     *
     * @param condition to check for.
     * @return number of condition occurences in the map
     */
    public static Integer getConditionStatistics(Condition condition) {
        return statistics.getOrDefault(condition.toString().toUpperCase(), 0);
    }

    public static void clearStatistics() {
        statistics.clear();
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
    }

    public static Set<Map.Entry<String, Integer>> getEntrySet() {
        return statistics.entrySet();
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
        for (Map.Entry<String, Integer> statistic : statistics.entrySet()) {
            stringBuilder.append(statistic.getKey())
                    .append(": ").append(statistic.getValue()).append(" occurences\n");
        }
        return stringBuilder.toString();
    }
}
