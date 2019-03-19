package seedu.address.model.restaurant;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.restaurant.categories.Category;
import seedu.address.model.restaurant.categories.Cuisine;
import seedu.address.model.restaurant.categories.Occasion;
import seedu.address.model.review.Review;
import seedu.address.model.tag.Tag;

/**
 * Represents a Restaurant in the food diary.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Restaurant {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Weblink weblink;
    private final List<Review> reviews = new ArrayList<>();
    private final OpeningHours openingHours;

    // Category fields
    private final Category categories;

    /**
     * Constructor for Restaurant class without Reviews and Categories
     * Every field must be present and not null.
     */
    public Restaurant(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Weblink weblink,
                      OpeningHours openingHours) {
        requireAllNonNull(name, phone, email, address, tags, weblink, openingHours);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.weblink = weblink;
        this.openingHours = openingHours;
        this.categories = Category.empty();
    }

    /**
     * Constructor for Restaurant without Reviews but with Optional Cuisine field.
     * Every field must be present and not null.
     */
    public Restaurant(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                      Weblink weblink, OpeningHours openingHours, Optional<Cuisine> cuisine) {
        requireAllNonNull(name, phone, email, address, tags, weblink, openingHours);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.weblink = weblink;
        this.openingHours = openingHours;
        this.categories = new Category(cuisine.isPresent() ? cuisine.get() : null, null);
    }

    /**
     * Create new restaurant with Categories and Reviews.
     */
    public Restaurant(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Weblink weblink,
                      OpeningHours openingHours, Category categories, List<Review> reviews) {
        requireAllNonNull(name, phone, email, address, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.weblink = weblink;
        this.openingHours = openingHours;
        this.reviews.addAll(reviews);
        if (categories == null) {
            this.categories = Category.empty();
        } else {
            this.categories = categories;
        }
    }

    /**
     * Creates a new restaurant from an existing restaurant with category set.
     * @param restaurant the restaurant to set cuisine to
     * @param category the cuisine to be set
     */
    public Restaurant(Restaurant restaurant, Category category) {
        requireAllNonNull(restaurant, category);
        this.name = restaurant.name;
        this.phone = restaurant.phone;
        this.email = restaurant.email;
        this.address = restaurant.address;
        this.tags.addAll(restaurant.tags);
        this.categories = category;
        this.weblink = restaurant.weblink;
        this.openingHours = restaurant.openingHours;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Weblink getWeblink() {
        return weblink;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public Optional<Cuisine> getCuisine() {
        return categories.getCuisine();
    }

    public Optional<Occasion> getOccasion() {
        return categories.getOccasion();
    }

    public Category getCategories() {
        return categories;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable review set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Review> getReviews() {
        return Collections.unmodifiableList(reviews);
    }

    /**
     * Returns true if both restaurants of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two restaurants.
     */
    public boolean isSameRestaurant(Restaurant otherRestaurant) {
        if (otherRestaurant == this) {
            return true;
        }

        return otherRestaurant != null
                && otherRestaurant.getName().equals(getName())
                && (otherRestaurant.getPhone().equals(getPhone()) || otherRestaurant.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both restaurants have the same identity and data fields.
     * This defines a stronger notion of equality between two restaurants.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Restaurant)) {
            return false;
        }

        Restaurant otherRestaurant = (Restaurant) other;
        return otherRestaurant.getName().equals(getName())
                && otherRestaurant.getPhone().equals(getPhone())
                && otherRestaurant.getEmail().equals(getEmail())
                && otherRestaurant.getAddress().equals(getAddress())
                && otherRestaurant.getTags().equals(getTags())
                && otherRestaurant.getWeblink().equals(getWeblink())
                && otherRestaurant.getOpeningHours().equals(getOpeningHours())
                && otherRestaurant.getReviews().equals(getReviews());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, weblink, openingHours, reviews);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Weblink: ")
                .append(getWeblink())
                .append(" Opening Hours: ")
                .append(getOpeningHours())
                .append(" Tags: ");
        getTags().forEach(builder::append);

        builder.append(" Categories: ")
                .append(categories.toString());

        builder.append(" Reviews: ");
        getReviews().forEach(builder::append);
        return builder.toString();
    }

}
