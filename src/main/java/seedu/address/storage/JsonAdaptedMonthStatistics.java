package seedu.address.storage;

import java.time.YearMonth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.record.MonthStatistics;
import seedu.address.model.record.Statistics;

/**
 * Jackson-friendly version of {@Link MonthStatistics}.
 */
public class JsonAdaptedMonthStatistics {

    private YearMonth yearMonth;
    private JsonAdaptedStatistics statistics;

    /**
     * Consutrcts a {@code MonthStatistics} with the given YearMonth and Statistics
     */
    @JsonCreator
    public JsonAdaptedMonthStatistics(@JsonProperty("yearMonth") YearMonth yearMonth,
                                      @JsonProperty("statistics") JsonAdaptedStatistics stats) {
        this.yearMonth = yearMonth;
        this.statistics = stats;
    }

    /**
     * Converts a given {@code MonthStatistics} into this class for Jackson use.
     */
    public JsonAdaptedMonthStatistics(MonthStatistics source) {
        this.yearMonth = source.getYearMonth();
        this.statistics = new JsonAdaptedStatistics(source.getStatistics());
    }

    /**
     * Converts this Jackson-friendly adapted MonthStatistics object into the model's {@code MonthStatistics} object.
     *
     * @throws IllegalArgumentException if there were any data constraints violated for MonthStatistics fields.
     */
    public MonthStatistics toModelType() throws IllegalArgumentException {
        Statistics stats = this.statistics.toModelType();
        return new MonthStatistics(this.yearMonth, stats);
    }
}
