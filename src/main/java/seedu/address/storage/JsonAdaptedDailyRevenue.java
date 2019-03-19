package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.statistics.DailyRevenue;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.Year;

/**
 * Jackson-friendly version of {@link Statistics}.
 */
class JsonAdaptedDailyRevenue {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Daily Revenue item's %s field is missing!";

    private final String day;
    private final String month;
    private final String year;
    private final String totalDailyRevenue;

    /**
     * Constructs a {@code JsonAdaptedDailyRevenue} with the given daily revenue details.
     */
    @JsonCreator
    public JsonAdaptedDailyRevenue(@JsonProperty("day") String day,
                                   @JsonProperty("month") String month,
                                   @JsonProperty("year") String year,
                                   @JsonProperty("totalDailyRevenue") String totalDailyRevenue) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalDailyRevenue = totalDailyRevenue;
    }

    /**
     * Converts a given {@code Bill} into this class for Jackson use.
     */
    public JsonAdaptedDailyRevenue(DailyRevenue source) {
        day = String.valueOf(source.getDay());
        month = String.valueOf(source.getMonth());
        year = String.valueOf(source.getYear());
        totalDailyRevenue = String.valueOf(source.getTotalDailyRevenue());
    }

    /**
     * Converts this Jackson-friendly adapted daily revenue object into the model's {@code DailyRevenue} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted daily revenue.
     */
    public DailyRevenue toModelType() throws IllegalValueException {
        if (day == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Day"));
        }

        if (!Day.isValidDay(day)) {
            throw new IllegalValueException(Day.MESSAGE_CONSTRAINTS);
        }

        if (month == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Month"));
        }

        if (!Month.isValidMonth(month)) {
            throw new IllegalValueException(Month.MESSAGE_CONSTRAINTS);
        }

        if (year == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Year"));
        }

        if (!Year.isValidYear(year)) {
            throw new IllegalValueException(Year.MESSAGE_CONSTRAINTS);
        }

        final Day modelDay = new Day(day);
        final Month modelMonth = new Month(month);
        final Year modelYear = new Year(year);
        final float modelTotalDailyRevenue = Float.parseFloat(totalDailyRevenue);

        return new DailyRevenue(modelDay, modelMonth, modelYear, modelTotalDailyRevenue);
    }

}
