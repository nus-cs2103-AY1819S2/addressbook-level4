package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.ReadOnlyStatistics;
import seedu.address.model.statistics.Statistics;

/**
 * An Immutable RestOrRant that is serializable to JSON format.
 */
@JsonRootName(value = "statisticsList")
class JsonSerializableStatistics {

    public static final String MESSAGE_DUPLICATE_ITEM = "Statistics list contains duplicate daily revenue(s).";
    private final List<JsonAdaptedDailyRevenue> statisticsList = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRestOrRant} with the given daily revenue.
     */
    @JsonCreator
    public JsonSerializableStatistics(@JsonProperty("statisticsList") List<JsonAdaptedDailyRevenue> statsList) {
        this.statisticsList.addAll(statsList);
    }

    /**
     * Converts a given {@code ReadOnlyStatistics} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRestOrRant}.
     */
    public JsonSerializableStatistics(ReadOnlyStatistics source) {
        statisticsList.addAll(source.getDailyRevenueList().stream().map(JsonAdaptedDailyRevenue::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code RestOrRant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Statistics toModelType() throws IllegalValueException {
        Statistics statistics = new Statistics();
        for (JsonAdaptedDailyRevenue jsonAdaptedDailyRevenue : statisticsList) {
            DailyRevenue dailyRevenue = jsonAdaptedDailyRevenue.toModelType();
            if (statistics.hasDailyRevenue(dailyRevenue)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            statistics.addDailyRevenue(dailyRevenue);
        }
        return statistics;
    }

}
