package seedu.travel.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.travel.model.place.Address;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.Description;
import seedu.travel.model.place.Name;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.Rating;
import seedu.travel.model.tag.Tag;
import seedu.travel.model.util.SampleDataUtil;

/**
 * A utility class to help with building Place objects.
 */
public class PlaceBuilder {

    public static final String DEFAULT_NAME = "Haw Par Villa";
    public static final String DEFAULT_COUNTRY_CODE = "SGP";
    public static final String DEFAULT_RATING = "4";
    public static final String DEFAULT_DESCRIPTION = "Unique park using giant statues & dioramas "
            + "to retell historic Chinese legends & religious mythology.";
    public static final String DEFAULT_ADDRESS = "262 Pasir Panjang Rd, Singapore 118628";

    private Name name;
    private CountryCode countryCode;
    private Rating rating;
    private Description description;
    private Address address;
    private Set<Tag> tags;

    public PlaceBuilder() {
        name = new Name(DEFAULT_NAME);
        countryCode = new CountryCode(DEFAULT_COUNTRY_CODE);
        rating = new Rating(DEFAULT_RATING);
        description = new Description(DEFAULT_DESCRIPTION);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PlaceBuilder with the data of {@code placeToCopy}.
     */
    public PlaceBuilder(Place placeToCopy) {
        name = placeToCopy.getName();
        countryCode = placeToCopy.getCountryCode();
        rating = placeToCopy.getRating();
        description = placeToCopy.getDescription();
        address = placeToCopy.getAddress();
        tags = new HashSet<>(placeToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Place} that we are building.
     */
    public PlaceBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code CountryCode} of the {@code Place} that we are building.
     */
    public PlaceBuilder withCountryCode(String countryCode) {
        this.countryCode = new CountryCode(countryCode);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Place} that we are building.
     */
    public PlaceBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Place} that we are building.
     */
    public PlaceBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code Place} that we are building.
     */
    public PlaceBuilder withRating(String rating) {
        this.rating = new Rating(rating);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Place} that we are building.
     */
    public PlaceBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Place build() {
        return new Place(name, countryCode, rating, description, address, tags);
    }

}
