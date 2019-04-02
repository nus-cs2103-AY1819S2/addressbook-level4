package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PostalData;
import seedu.address.model.restaurant.Postal;


/**
 * Jackson-friendly version of {@link PostalData}.
 */
class JsonAdaptedPostalData {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "MISSING";
    private final String postal;
    private final double x;
    private final double y;


    /**
     * Constructs a {@code JsonAdaptedPostalData} with the given postal code details.
     */
    @JsonCreator
    public JsonAdaptedPostalData(@JsonProperty("postal") String postal, @JsonProperty("x") double x,
                                 @JsonProperty("y") double y) {

        this.postal = postal;
        this.x = x;
        this.y = y;
    }

    /**
     * Converts a given {@code PostalData} into this class for Jackson use.
     *
     * Converts this Jackson-friendly adapted PostalData object into the model's {@code PostalData} object.
     */
    public PostalData toModelType() throws IllegalValueException {
        if (postal == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Postal.class.getSimpleName()));
        }
        if (!Postal.isValidPostal(postal)) {
            throw new IllegalValueException(Postal.MESSAGE_CONSTRAINTS);
        }
        return new PostalData(postal, x, y);
    }

}
