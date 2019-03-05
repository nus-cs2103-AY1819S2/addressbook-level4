package seedu.address.model.restaurant;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.restaurant.categories.Cuisine;
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
    private final Set<Review> reviews = new HashSet<>();

    // Category fields
    private final Optional<Cuisine> cuisine;

    /**
     * Constructor for Restaurant class without Reviews and Cuisine
     * Every field must be present and not null.
     */
    public Restaurant(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Weblink weblink) {
        requireAllNonNull(name, phone, email, address, tags, weblink);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.weblink = weblink;
        this.cuisine = Optional.empty();
    }

    /**
     * Constructor for Restaurant without Reviews but with Optional Cuisine field.
     * Every field must be present and not null.
     */
    public Restaurant(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Optional<Cuisine> cuisine,
                      Weblink weblink) {
        requireAllNonNull(name, phone, email, address, tags, weblink);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.weblink = weblink;
        this.cuisine = cuisine;
    }

    /**
     * Constructor for Restaurant with Reviews and Optional Cuisine field.
     * This constructor is used for AddReviewCommand.
     * Every field must be present and not null.
     */
    public Restaurant(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Optional<Cuisine> cuisine,
                      Weblink weblink, Set<Review> reviews) {
        requireAllNonNull(name, phone, email, address, tags, weblink);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.weblink = weblink;
        this.cuisine = cuisine;
        this.reviews.addAll(reviews);
    }

    /**
     * Every field must be present and not null.
     */
    public Restaurant(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this(name, phone, email, address, tags, Weblink.makeDefaultWeblink());
    }

    /**
     * Constructor for Restaurant class with Reviews and without Cuisine.
     * Every field except reviews must be present and not null.
     */
    public Restaurant(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Weblink weblink,
                      Set<Review> reviews) {
        requireAllNonNull(name, phone, email, address, weblink, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.weblink = weblink;
        this.reviews.addAll(reviews);
        this.cuisine = Optional.empty();
    }

    /**
     * Create new restaurant with Optional cuisine field, without Reviews.
     */
    public Restaurant(Name name, Phone phone, Email email, Address address, Set<Tag> tags, Optional<Cuisine> cuisine) {
        requireAllNonNull(name, phone, email, address, tags, cuisine);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.cuisine = cuisine;
        this.weblink = Weblink.makeDefaultWeblink();
    }

    /**
     * Create new restaurant with Optional cuisine field and Reviews.
     */
    public Restaurant(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                      Optional<Cuisine> cuisine, Set<Review> reviews) {
        requireAllNonNull(name, phone, email, address, tags, cuisine);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.cuisine = cuisine;
        this.weblink = Weblink.makeDefaultWeblink();
    }

    /**
     * Creates a new restaurant from an existing restaurant with cuisine set.
     * @param restaurant the restaurant to set cuisine to
     * @param cuisine the cuisine to be set
     */
    public Restaurant(Restaurant restaurant, Cuisine cuisine) {
        requireAllNonNull(restaurant, cuisine);
        this.name = restaurant.name;
        this.phone = restaurant.phone;
        this.email = restaurant.email;
        this.address = restaurant.address;
        this.tags.addAll(restaurant.tags);
        this.cuisine = Optional.of(cuisine);
        this.weblink = Weblink.makeDefaultWeblink();
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

    public Optional<Cuisine> getCuisine() {
        return cuisine;
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
    public Set<Review> getReviews() {
        return Collections.unmodifiableSet(reviews);
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
                && otherRestaurant.getReviews().equals(getReviews());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, reviews);
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
                .append(" Tags: ");
        getTags().forEach(builder::append);

        this.cuisine.ifPresent(content ->
            builder.append(" Cuisine: ")
                    .append(content)
        );

        builder.append(" Reviews: ");
        getReviews().forEach(builder::append);
        return builder.toString();
    }

}
