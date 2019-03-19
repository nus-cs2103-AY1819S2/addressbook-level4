package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.PostalData;


/**
 * Jackson-friendly version of {@link PostalData}.
 */
class JsonAdaptedPostalData {

    private final String postal;
    private final String x;
    private final String y;


    /**
     * Constructs a {@code JsonAdaptedPostalData} with the given postal code details.
     */
    @JsonCreator
    public JsonAdaptedPostalData(@JsonProperty("postal") String postal, @JsonProperty("x") String x,
                                 @JsonProperty("y") String y) {

        this.postal = postal;
        this.x = x;
        this.y = y;
    }

    /**
     * Converts a given {@code PostalData} into this class for Jackson use.
     *
     * Converts this Jackson-friendly adapted PostalData object into the model's {@code PostalData} object.
     */
    public PostalData toModelType() {
        return new PostalData(postal, x, y);
    }

}
