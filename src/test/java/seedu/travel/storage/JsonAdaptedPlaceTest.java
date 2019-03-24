package seedu.travel.storage;

import static org.junit.Assert.assertEquals;
import static seedu.travel.storage.JsonAdaptedPlace.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.travel.testutil.TypicalPlaces.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.travel.commons.exceptions.IllegalValueException;
import seedu.travel.model.place.Address;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.DateVisited;
import seedu.travel.model.place.Description;
import seedu.travel.model.place.Name;
import seedu.travel.model.place.Rating;
import seedu.travel.testutil.Assert;

public class JsonAdaptedPlaceTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_COUNTRY_CODE = "@GP";
    private static final String INVALID_DATE_VISITED = "10!99*2000";
    private static final String INVALID_RATING = "65";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_DESCRIPTION = " Invalid description"; // must begin with alphabet
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_COUNTRY_CODE = BENSON.getCountryCode().toString();
    private static final String VALID_DATE_VISITED = BENSON.getDateVisited().toString();
    private static final String VALID_RATING = BENSON.getRating().toString();
    private static final String VALID_DESCRIPTION = BENSON.getDescription().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPlaceDetails_returnsPlace() throws Exception {
        JsonAdaptedPlace place = new JsonAdaptedPlace(BENSON);
        assertEquals(BENSON, place.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPlace place =
                new JsonAdaptedPlace(INVALID_NAME, VALID_COUNTRY_CODE, VALID_DATE_VISITED, VALID_RATING,
                    VALID_DESCRIPTION, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, place::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPlace place = new JsonAdaptedPlace(null, VALID_COUNTRY_CODE, VALID_DATE_VISITED, VALID_RATING,
                VALID_DESCRIPTION, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, place::toModelType);
    }

    @Test
    public void toModelType_invalidCountryCode_throwsIllegalValueException() {
        JsonAdaptedPlace place = new JsonAdaptedPlace(VALID_NAME, INVALID_COUNTRY_CODE, VALID_DATE_VISITED,
                VALID_RATING, VALID_DESCRIPTION, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = CountryCode.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, place::toModelType);
    }

    @Test
    public void toModelType_nullCountryCode_throwsIllegalValueException() {
        JsonAdaptedPlace place = new JsonAdaptedPlace(VALID_NAME, null, VALID_DATE_VISITED, VALID_RATING,
                VALID_DESCRIPTION, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, CountryCode.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, place::toModelType);
    }

    @Test
    public void toModelType_invalidDateVisited_throwsIllegalValueException() {
        JsonAdaptedPlace place = new JsonAdaptedPlace(VALID_NAME, VALID_COUNTRY_CODE, INVALID_DATE_VISITED,
                VALID_RATING, VALID_DESCRIPTION, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = DateVisited.MESSAGE_INCORRECT_FORMAT;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, place::toModelType);
    }

    @Test
    public void toModelType_nullDateVisited_throwsIllegalValueException() {
        JsonAdaptedPlace place = new JsonAdaptedPlace(VALID_NAME, VALID_COUNTRY_CODE, null, VALID_RATING,
            VALID_DESCRIPTION, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateVisited.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, place::toModelType);
    }

    @Test
    public void toModelType_invalidRating_throwsIllegalValueException() {
        JsonAdaptedPlace place =
                new JsonAdaptedPlace(VALID_NAME, VALID_COUNTRY_CODE, VALID_DATE_VISITED, INVALID_RATING,
                    VALID_DESCRIPTION, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Rating.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, place::toModelType);
    }

    @Test
    public void toModelType_nullRating_throwsIllegalValueException() {
        JsonAdaptedPlace place = new JsonAdaptedPlace(VALID_NAME, VALID_COUNTRY_CODE, VALID_DATE_VISITED, null,
                VALID_DESCRIPTION, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Rating.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, place::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedPlace place =
                new JsonAdaptedPlace(VALID_NAME, VALID_COUNTRY_CODE, VALID_DATE_VISITED, VALID_RATING,
                    INVALID_DESCRIPTION, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, place::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedPlace place = new JsonAdaptedPlace(VALID_NAME, VALID_COUNTRY_CODE, VALID_DATE_VISITED,
                VALID_RATING, null, VALID_ADDRESS, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, place::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPlace place =
                new JsonAdaptedPlace(VALID_NAME, VALID_COUNTRY_CODE, VALID_DATE_VISITED, VALID_RATING,
                    VALID_DESCRIPTION, INVALID_ADDRESS, VALID_TAGS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, place::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPlace place = new JsonAdaptedPlace(VALID_NAME, VALID_COUNTRY_CODE, VALID_DATE_VISITED,
                VALID_RATING, VALID_DESCRIPTION, null, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, place::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPlace place = new JsonAdaptedPlace(VALID_NAME, VALID_COUNTRY_CODE, VALID_DATE_VISITED,
                VALID_RATING, VALID_DESCRIPTION, VALID_ADDRESS, invalidTags);
        Assert.assertThrows(IllegalValueException.class, place::toModelType);
    }

}
