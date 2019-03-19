package seedu.address.model.place;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Place in TravelBuddy.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Place {

    // Identity fields
    private final Name name;
    private final CountryCode countryCode;
    private final Rating rating;
    private final Description description;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Place(Name name, CountryCode countryCode, Rating rating, Description description, Address address,
        Set<Tag> tags) {
        requireAllNonNull(name, countryCode, rating, description, address, tags);
        this.name = name;
        this.countryCode = countryCode;
        this.rating = rating;
        this.description = description;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public Rating getRating() {
        return rating;
    }

    public Description getDescription() {
        return description;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both places of the same name also have the same phone number.
     * This defines a weaker notion of equality between two places.
     */
    public boolean isSamePlace(Place otherPlace) {
        if (otherPlace == this) {
            return true;
        }

        return otherPlace != null && otherPlace.getName().equals(getName());
    }

    /**
     * Returns true if both places have the same identity and data fields.
     * This defines a stronger notion of equality between two places.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Place)) {
            return false;
        }

        Place otherPlace = (Place) other;
        return otherPlace.getName().equals(getName())
                && otherPlace.getCountryCode().equals(getCountryCode())
                && otherPlace.getRating().equals(getRating())
                && otherPlace.getDescription().equals(getDescription())
                && otherPlace.getAddress().equals(getAddress())
                && otherPlace.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, countryCode, rating, description, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Country Code: ")
                .append(getCountryCode())
                .append(" Rating: ")
                .append(getRating())
                .append(" Description: ")
                .append(getDescription())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
