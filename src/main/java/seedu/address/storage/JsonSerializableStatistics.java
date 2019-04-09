package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.statistics.ReadOnlyStatistics;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.statistics.Statistics;

/**
 * An Immutable RestOrRant that is serializable to JSON format.
 */
@JsonRootName(value = "statisticsList")
class JsonSerializableStatistics {

    public static final String MESSAGE_DUPLICATE_ITEM = "Statistics list contains duplicate revenue(s).";
    private final List<JsonAdaptedRevenue> statisticsList = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRestOrRant} with the given revenue.
     */
    @JsonCreator
    public JsonSerializableStatistics(@JsonProperty("statisticsList") List<JsonAdaptedRevenue> statsList) {
        this.statisticsList.addAll(statsList);
    }

    /**
     * Converts a given {@code ReadOnlyStatistics} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRestOrRant}.
     */
    public JsonSerializableStatistics(ReadOnlyStatistics source) {
        statisticsList.addAll(source.getRevenueList().stream().map(JsonAdaptedRevenue::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code RestOrRant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Statistics toModelType() throws IllegalValueException {
        Statistics statistics = new Statistics();
        for (JsonAdaptedRevenue jsonAdaptedRevenue : statisticsList) {
            Revenue revenue = jsonAdaptedRevenue.toModelType();
            if (statistics.hasRevenue(revenue)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ITEM);
            }
            statistics.addRevenue(revenue);
        }
        return statistics;
    }

}
