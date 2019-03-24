package seedu.travel.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.travel.commons.exceptions.IllegalValueException;
import seedu.travel.model.place.Address;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.DateVisited;
import seedu.travel.model.place.Description;
import seedu.travel.model.place.Name;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.Rating;
import seedu.travel.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Place}.
 */
class JsonAdaptedPlace {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Place's %s field is missing!";

    private final String name;
    private final String countryCode;
    private final String dateVisited;
    private final String rating;
    private final String description;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPlace} with the given place details.
     */
    @JsonCreator
    public JsonAdaptedPlace(@JsonProperty("name") String name,
                            @JsonProperty("countryCode") String countryCode,
                            @JsonProperty("dateVisited") String dateVisited,
                            @JsonProperty("rating") String rating,
                            @JsonProperty("description") String description,
                            @JsonProperty("address") String address,
                            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.countryCode = countryCode;
        this.dateVisited = dateVisited;
        this.rating = rating;
        this.description = description;
        this.address = address;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Place} into this class for Jackson use.
     */
    public JsonAdaptedPlace(Place source) {
        name = source.getName().fullName;
        countryCode = source.getCountryCode().code;
        dateVisited = source.getDateVisited().date;
        rating = source.getRating().value;
        description = source.getDescription().value;
        address = source.getAddress().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted place object into the model's {@code Place} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted place.
     */
    public Place toModelType() throws IllegalValueException {
        final List<Tag> placeTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            placeTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (countryCode == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                CountryCode.class.getSimpleName()));
        }
        if (!CountryCode.isValidCountryCode(countryCode)) {
            throw new IllegalValueException(CountryCode.MESSAGE_CONSTRAINTS);
        }
        final CountryCode modelCountryCode = new CountryCode(countryCode);

        if (dateVisited == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                DateVisited.class.getSimpleName()));
        }
        if (!DateVisited.isCorrectDateFormat(dateVisited)) {
            throw new IllegalValueException(DateVisited.MESSAGE_INCORRECT_FORMAT);
        }
        if (!DateVisited.isValidDateVisited(dateVisited)) {
            throw new IllegalValueException(DateVisited.MESSAGE_FUTURE_DATE_ADDED);
        }
        final DateVisited modelDateVisited = new DateVisited(dateVisited);

        if (rating == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName()));
        }
        if (!Rating.isValidRating(rating)) {
            throw new IllegalValueException(Rating.MESSAGE_CONSTRAINTS);
        }
        final Rating modelPhone = new Rating(rating);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(description);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(placeTags);
        return new Place(modelName, modelCountryCode, modelDateVisited, modelPhone, modelDescription, modelAddress,
            modelTags);
    }

}
