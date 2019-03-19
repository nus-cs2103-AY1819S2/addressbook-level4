package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedRestaurant.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.TypicalRestaurants.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.testutil.Assert;

public class JsonAdaptedRestaurantTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String[] INVALID_REVIEW = {"2019-03-03", " bad review", "-6.9"};
    private static final String INVALID_CUISINE = "Fast$Food";
    private static final String INVALID_WEBLINK = "kfc@com";
    private static final String INVALID_OPENING_HOURS = "12 to 230";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedReview> VALID_REVIEWS = BENSON.getReviews().stream()
            .map(JsonAdaptedReview::new)
            .collect(Collectors.toList());
    private static final String VALID_CUISINE = BENSON.getCuisine().toString();
    private static final String VALID_WEBLINK = BENSON.getWeblink().toString();
    private static final String VALID_OPENING_HOURS = BENSON.getOpeningHours().toString();

    @Test
    public void toModelType_validRestaurantDetails_returnsRestaurant() throws Exception {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(BENSON);
        assertEquals(BENSON, restaurant.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_CUISINE, null, VALID_WEBLINK, VALID_OPENING_HOURS, VALID_REVIEWS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_CUISINE, null, VALID_WEBLINK, VALID_OPENING_HOURS, VALID_REVIEWS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_CUISINE, null, VALID_WEBLINK, VALID_OPENING_HOURS, VALID_REVIEWS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(VALID_NAME, null, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_CUISINE, null, VALID_WEBLINK, VALID_OPENING_HOURS, VALID_REVIEWS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_CUISINE, null, VALID_WEBLINK, VALID_OPENING_HOURS , VALID_REVIEWS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(VALID_NAME, VALID_PHONE, null,
                VALID_ADDRESS, VALID_TAGS, VALID_CUISINE, null, VALID_WEBLINK, VALID_OPENING_HOURS, VALID_REVIEWS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                INVALID_ADDRESS, VALID_TAGS, VALID_CUISINE, null, VALID_WEBLINK, VALID_OPENING_HOURS, VALID_REVIEWS);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                null, VALID_TAGS, VALID_CUISINE, null, VALID_WEBLINK, VALID_OPENING_HOURS, VALID_REVIEWS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, invalidTags, VALID_CUISINE, null, VALID_WEBLINK, VALID_OPENING_HOURS, VALID_REVIEWS);
        Assert.assertThrows(IllegalValueException.class, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidReviews_throwsIllegalValueException() {
        List<JsonAdaptedReview> invalidReviews = new ArrayList<>(VALID_REVIEWS);
        invalidReviews.add(new JsonAdaptedReview(INVALID_REVIEW[0], INVALID_REVIEW[1], INVALID_REVIEW[2]));
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, VALID_CUISINE, null, VALID_WEBLINK, VALID_OPENING_HOURS, invalidReviews);
        Assert.assertThrows(IllegalValueException.class, restaurant::toModelType);
    }

    @Test
    public void toModelType_invalidCuisine_throwsIllegalValueException() {
        JsonAdaptedRestaurant restaurant = new JsonAdaptedRestaurant(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_TAGS, INVALID_CUISINE, null, VALID_WEBLINK, VALID_OPENING_HOURS, VALID_REVIEWS);
        String expectedMessage = Cuisine.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, restaurant::toModelType);
    }

}
