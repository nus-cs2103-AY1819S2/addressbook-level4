package seedu.address.logic.commands;

import seedu.address.model.tag.Condition;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Class that represents a TreeMap of conditions and their respective occurences
 */
public class Statistics {

    private static Map<Condition, Integer> statistics;

    public Statistics() {
        statistics = new TreeMap<>();
    }

    public static Map<Condition, Integer> getStatistics() {
        return statistics;
    }

    /**
     * Returns the number of times the specific condition has appeared in added requests
     *
     * @param condition  to check for.
     * @return number of condition occurences in the map
     */
    public static Integer getConditionStatistics(Condition condition){
        if(containsCondition(condition))
            return statistics.get(condition);
        else
            return 0;
    }

    public static Integer getSize(){
        return statistics.size();
    }

    /**
     * Checks if a given condition is in the current statistics tree map.
     *
     * @param condition to check for.
     * @return true if condition is in the map, false otherwise
     */
    public static boolean containsCondition(Condition condition) {
        return statistics.containsKey(condition);
    }

    /**
     * Updates statistics tree map every time a new request is added
     * If it is a new condition, a new entry in the tree map will be made
     * Else, the integer value will be incremented
     *
     * @param conditionSet to check for.
     * @return true if condition is in the map, false otherwise
     */
    public static void updateStatistics(Set<Condition> conditionSet){
        for(Condition condition : conditionSet) {
            Integer count = statistics.get(condition);
            statistics.put(condition, (count == null) ? 1 : count + 1);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Conditions and Occurences:\n");
        for (Map.Entry<Condition, Integer> statistic : statistics.entrySet()) {
            stringBuilder.append(statistic.getKey())
                    .append(": ").append(statistic.getValue()).append(" occurences");
        }
        return stringBuilder.toString();
    }
}
