package seedu.travel.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.travel.commons.exceptions.IllegalValueException;
import seedu.travel.model.ReadOnlyTravelBuddy;
import seedu.travel.model.TravelBuddy;
import seedu.travel.model.place.Place;

/**
 * An Immutable TravelBuddy that is serializable to JSON format.
 */
@JsonRootName(value = "travelBuddy")
class JsonSerializableTravelBuddy {

    public static final String MESSAGE_DUPLICATE_PLACE = "Places list contains duplicate place(s).";

    private final List<JsonAdaptedPlace> places = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTravelBuddy} with the given places.
     */
    @JsonCreator
    public JsonSerializableTravelBuddy(@JsonProperty("places") List<JsonAdaptedPlace> places) {
        this.places.addAll(places);
    }

    /**
     * Converts a given {@code ReadOnlyTravelBuddy} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTravelBuddy}.
     */
    public JsonSerializableTravelBuddy(ReadOnlyTravelBuddy source) {
        places.addAll(source.getPlaceList().stream().map(JsonAdaptedPlace::new).collect(Collectors.toList()));
    }

    /**
     * Converts this travel book into the model's {@code TravelBuddy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TravelBuddy toModelType() throws IllegalValueException {
        TravelBuddy travelBuddy = new TravelBuddy();
        for (JsonAdaptedPlace jsonAdaptedPlace : places) {
            Place place = jsonAdaptedPlace.toModelType();
            if (travelBuddy.hasPlace(place)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PLACE);
            }
            travelBuddy.addPlace(place);
        }
        return travelBuddy;
    }

}
