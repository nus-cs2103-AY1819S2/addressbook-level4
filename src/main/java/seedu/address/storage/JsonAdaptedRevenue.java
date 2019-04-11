package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.statistics.Day;
import seedu.address.model.statistics.Month;
import seedu.address.model.statistics.Revenue;
import seedu.address.model.statistics.Statistics;
import seedu.address.model.statistics.Year;

/**
 * Jackson-friendly version of {@link Statistics}.
 */
class JsonAdaptedRevenue {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Revenue item's %s field is missing!";

    private final String day;
    private final String month;
    private final String year;
    private final String totalRevenue;

    /**
     * Constructs a {@code JsonAdaptedRevenue} with the given revenue details.
     */
    @JsonCreator
    public JsonAdaptedRevenue(@JsonProperty("day") String day,
                              @JsonProperty("month") String month,
                              @JsonProperty("year") String year,
                              @JsonProperty("totalRevenue") String totalRevenue) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.totalRevenue = totalRevenue;
    }

    /**
     * Converts a given {@code Bill} into this class for Jackson use.
     */
    public JsonAdaptedRevenue(Revenue source) {
        day = String.valueOf(source.getDay());
        month = String.valueOf(source.getMonth());
        year = String.valueOf(source.getYear());
        totalRevenue = String.valueOf(source.getTotalRevenue());
    }

    /**
     * Converts this Jackson-friendly adapted revenue object into the model's {@code Revenue} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted revenue.
     */
    public Revenue toModelType() throws IllegalValueException {
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

        if (totalRevenue == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "TotalRevenue"));
        }

        try {
            Float.parseFloat(totalRevenue);
        } catch (NumberFormatException nfe) {
            throw new IllegalValueException("Total revenue should be a number.");
        }

        final Day modelDay = new Day(day);
        final Month modelMonth = new Month(month);
        final Year modelYear = new Year(year);

        final float modelTotalRevenue = Float.parseFloat(totalRevenue);

        return new Revenue(modelDay, modelMonth, modelYear, modelTotalRevenue);
    }

}
