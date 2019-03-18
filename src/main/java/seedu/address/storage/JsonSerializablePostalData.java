package seedu.address.storage;

import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.model.PostalData;
import seedu.address.model.PostalDataSet;

/**
 * An Immutable FoodDiary that is serializable to JSON format.
 */
@JsonRootName(value = "postaldata")
class JsonSerializablePostalData {

    private final HashSet<JsonAdaptedPostalData> postalData = new HashSet<>();

    /**
     * Constructs a {@code JsonSerializablePostalData} with the given postalData.
     */
    @JsonCreator
    public JsonSerializablePostalData(@JsonProperty("postaldata") List<JsonAdaptedPostalData> postalData) {
        this.postalData.addAll(postalData);
    }


    /**
     * Converts this postalData into the model's {@code PostalDataSet} object.
     *
     */
    public PostalDataSet toModelType() {
        PostalDataSet postalDataSet = new PostalDataSet();
        for (JsonAdaptedPostalData jsonAdaptedPostalData : postalData) {
            PostalData postalData = jsonAdaptedPostalData.toModelType();
            postalDataSet.addData(postalData);
        }
        return postalDataSet;
    }

}
