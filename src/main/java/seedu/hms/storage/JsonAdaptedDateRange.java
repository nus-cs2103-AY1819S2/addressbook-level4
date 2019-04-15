package seedu.hms.storage;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.hms.commons.exceptions.IllegalValueException;
import seedu.hms.model.util.DateRange;

/**
 * Jackson-friendly version of {@link DateRange}.
 */
class JsonAdaptedDateRange {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Date Range's %s field is missing!";

    private final Calendar startDate;
    private final Calendar endDate;

    /**
     * Constructs a {@code JsonAdaptedCustomer} with the given customer details.
     */
    @JsonCreator
    public JsonAdaptedDateRange(@JsonProperty("startDate") Calendar startDate,
                                @JsonProperty("endDate") Calendar endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Converts a given {@code Customer} into this class for Jackson use.
     */
    public JsonAdaptedDateRange(DateRange source) {
        startDate = source.getStartDate();
        endDate = source.getEndDate();
    }

    /**
     * Converts this Jackson-friendly adapted customer object into the model's {@code Customer} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted customer.
     */
    public DateRange toModelType() throws IllegalValueException {

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "start date"));
        }

        if (startDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "end date"));
        }

        return new DateRange(startDate, endDate);
    }

}
