package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.OpeningHours;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;
import seedu.address.model.restaurant.categories.Category;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.review.Review;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Restaurant objects.
 */
public class RestaurantBuilder {

    public static final String DEFAULT_NAME = "Kentucky Fried Chicken";
    public static final String DEFAULT_PHONE = "65355255";
    public static final String DEFAULT_EMAIL = "kfc@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_WEBLINK = Weblink.NO_WEBLINK_STRING;
    public static final String DEFAULT_OPENING_HOURS = "0930 to 2130";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Weblink weblink;
    private OpeningHours openingHours;
    private List<Review> reviews;
    private Category categories;

    public RestaurantBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        reviews = new ArrayList<>();
        tags = new HashSet<>();
        weblink = new Weblink(DEFAULT_WEBLINK);
        openingHours = new OpeningHours(DEFAULT_OPENING_HOURS);
        categories = null;
    }

    /**
     * Initializes the RestaurantBuilder with the data of {@code restaurantToCopy}.
     */
    public RestaurantBuilder(Restaurant restaurantToCopy) {
        name = restaurantToCopy.getName();
        phone = restaurantToCopy.getPhone();
        email = restaurantToCopy.getEmail();
        address = restaurantToCopy.getAddress();
        reviews = restaurantToCopy.getReviews();
        tags = new HashSet<>(restaurantToCopy.getTags());
        weblink = restaurantToCopy.getWeblink();
        openingHours = restaurantToCopy.getOpeningHours();
        reviews = new ArrayList<>(restaurantToCopy.getReviews());
        categories = restaurantToCopy.getCategories();
    }

    /**
     * Sets the {@code Name} of the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code reviews} into a {@code List<Review>} and set it to the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withReviews(String ... reviews) {
        this.reviews = SampleDataUtil.getReviewList(reviews);
        return this;
    }

    /**
     * Sets the {@code List<Review>} to the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withWeblink(String weblink) {
        this.weblink = new Weblink(weblink);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withOpeningHours(String openingHours) {
        this.openingHours = new OpeningHours(openingHours);
        return this;
    }

    /**
     * Sets the {@code Cuisine} of the {@code Restaurant} that we are building.
     */
    public RestaurantBuilder withCategories(String cuisine) {
        this.categories = new Category(new Cuisine(cuisine), null);
        return this;
    }

    /**
     * Builds a Restaurant
     */
    public Restaurant build() {
        return new Restaurant(name, phone, email, address, tags, weblink, openingHours,
                categories, reviews);
    }
}
