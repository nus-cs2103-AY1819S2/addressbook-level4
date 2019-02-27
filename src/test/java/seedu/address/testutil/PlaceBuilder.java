package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.place.Address;
import seedu.address.model.place.Email;
import seedu.address.model.place.Name;
import seedu.address.model.place.Phone;
import seedu.address.model.place.Place;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Place objects.
 */
public class PlaceBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;

    public PlaceBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PlaceBuilder with the data of {@code placeToCopy}.
     */
    public PlaceBuilder(Place placeToCopy) {
        name = placeToCopy.getName();
        phone = placeToCopy.getPhone();
        email = placeToCopy.getEmail();
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
     * Sets the {@code Phone} of the {@code Place} that we are building.
     */
    public PlaceBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Place} that we are building.
     */
    public PlaceBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Place build() {
        return new Place(name, phone, email, address, tags);
    }

}
