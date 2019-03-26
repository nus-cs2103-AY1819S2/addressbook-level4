package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.OpeningHours;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;
import seedu.address.model.restaurant.categories.Category;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.restaurant.categories.Occasion;
import seedu.address.model.review.Review;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Restaurant}.
 */
class JsonAdaptedRestaurant {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Restaurant's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();
    private final List<JsonAdaptedReview> reviewed = new ArrayList<>();
    private final String weblink;
    private final String openingHours;
    private final String cuisine;
    private final String occasion;

    /**
     * Constructs a {@code JsonAdaptedRestaurant} with the given restaurant details.
     */
    @JsonCreator
    public JsonAdaptedRestaurant(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
            @JsonProperty("cuisine") String cuisine,
            @JsonProperty("occasion") String occasion,
            @JsonProperty("weblink") String weblink,
            @JsonProperty("openinghours") String openingHours,
            @JsonProperty("reviewed") List<JsonAdaptedReview> reviewed) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.cuisine = cuisine;
        this.occasion = occasion;
        this.weblink = weblink;
        this.openingHours = openingHours;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
        if (reviewed != null) {
            this.reviewed.addAll(reviewed);
        }
    }

    /**
     * Converts a given {@code Restaurant} into this class for Jackson use.
     */
    public JsonAdaptedRestaurant(Restaurant source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        reviewed.addAll(source.getReviews().stream()
                .map(JsonAdaptedReview::new)
                .collect(Collectors.toList()));

        if (source.getCuisine().isPresent()) {
            cuisine = source.getCuisine().get().value;
        } else {
            cuisine = null;
        }

        if (source.getOccasion().isPresent()) {
            occasion = source.getOccasion().get().value;
        } else {
            occasion = null;
        }

        weblink = source.getWeblink().value;
        openingHours = source.getOpeningHours().value;
    }

    /**
     * Converts this Jackson-friendly adapted restaurant object into the model's {@code Restaurant} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted restaurant.
     */
    public Restaurant toModelType() throws IllegalValueException {
        final List<Tag> restaurantTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            restaurantTags.add(tag.toModelType());
        }

        final List<Review> restaurantReviews = new ArrayList<>();
        for (JsonAdaptedReview review : reviewed) {
            restaurantReviews.add(review.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Cuisine modelCuisine;
        if (cuisine == null) {
            modelCuisine = null;
        } else {
            if (!Cuisine.isValidCuisine(cuisine)) {
                throw new IllegalValueException(Cuisine.MESSAGE_CONSTRAINTS);
            }
            modelCuisine = new Cuisine(cuisine);
        }

        final Occasion modelOccasion;
        if (occasion == null) {
            modelOccasion = null;
        } else {
            if (!Occasion.isValidOccasion(occasion)) {
                throw new IllegalValueException(Occasion.MESSAGE_CONSTRAINTS);
            }
            modelOccasion = new Occasion(occasion);
        }

        if (weblink == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Weblink.class.getSimpleName()));
        }
        if (!Weblink.isValidWeblinkString(weblink)) {
            throw new IllegalValueException(Weblink.MESSAGE_CONSTRAINTS);
        }
        final Weblink modelWeblink = new Weblink(weblink);

        if (openingHours == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OpeningHours.class.getSimpleName()));
        }
        if (!OpeningHours.isValidOpeningHour(openingHours)) {
            throw new IllegalValueException(OpeningHours.MESSAGE_CONSTRAINTS);
        }
        final OpeningHours modelOpeningHours = new OpeningHours(openingHours);

        final Set<Tag> modelTags = new HashSet<>(restaurantTags);

        final ArrayList<Review> modelReviews = new ArrayList<>(restaurantReviews);

        return new Restaurant(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelWeblink,
                modelOpeningHours, new Category(modelCuisine, modelOccasion), modelReviews);
    }

}
