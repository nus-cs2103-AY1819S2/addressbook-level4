package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditRestaurantDescriptor;
import seedu.address.model.restaurant.Address;
import seedu.address.model.restaurant.Email;
import seedu.address.model.restaurant.Name;
import seedu.address.model.restaurant.OpeningHours;
import seedu.address.model.restaurant.Phone;
import seedu.address.model.restaurant.Postal;
import seedu.address.model.restaurant.Restaurant;
import seedu.address.model.restaurant.Weblink;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditRestaurantDescriptor objects.
 */
public class EditRestaurantDescriptorBuilder {

    private EditRestaurantDescriptor descriptor;

    public EditRestaurantDescriptorBuilder() {
        descriptor = new EditRestaurantDescriptor();
    }

    public EditRestaurantDescriptorBuilder(EditRestaurantDescriptor descriptor) {
        this.descriptor = new EditRestaurantDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRestaurantDescriptor} with fields containing {@code restaurant}'s details
     */
    public EditRestaurantDescriptorBuilder(Restaurant restaurant) {
        descriptor = new EditRestaurantDescriptor();
        descriptor.setName(restaurant.getName());
        descriptor.setPhone(restaurant.getPhone());
        descriptor.setEmail(restaurant.getEmail());
        descriptor.setAddress(restaurant.getAddress());
        descriptor.setPostal(restaurant.getPostal());
        descriptor.setTags(restaurant.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditRestaurantDescriptor} that we are building.
     */
    public EditRestaurantDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditRestaurantDescriptor} that we are building.
     */
    public EditRestaurantDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Postal} of the {@code EditRestaurantDescriptor} that we are building.
     */
    public EditRestaurantDescriptorBuilder withPostal(String postal) {
        descriptor.setPostal(new Postal(postal));
        return this;
    }


    /**
     * Sets the {@code Email} of the {@code EditRestaurantDescriptor} that we are building.
     */
    public EditRestaurantDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditRestaurantDescriptor} that we are building.
     */
    public EditRestaurantDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditRestaurantDescriptor}
     * that we are building.
     */
    public EditRestaurantDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code Weblink} of the {@code EditRestaurantDescriptor} that we are building.
     */
    public EditRestaurantDescriptorBuilder withWeblink(String weblink) {
        descriptor.setWeblink(new Weblink(weblink));
        return this;
    }

    /**
     * Sets the {@code OpeningHours} of the {@code EditRestaurantDescriptor} that we are building.
     */
    public EditRestaurantDescriptorBuilder withOpeningHours(String openingHours) {
        descriptor.setOpeningHours(new OpeningHours(openingHours));
        return this;
    }

    public EditRestaurantDescriptor build() {
        return descriptor;
    }

}
