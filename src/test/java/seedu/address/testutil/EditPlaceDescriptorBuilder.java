package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPlaceDescriptor;
import seedu.address.model.place.Address;
import seedu.address.model.place.Description;
import seedu.address.model.place.Name;
import seedu.address.model.place.Place;
import seedu.address.model.place.Rating;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPlaceDescriptor objects.
 */
public class EditPlaceDescriptorBuilder {

    private EditPlaceDescriptor descriptor;

    public EditPlaceDescriptorBuilder() {
        descriptor = new EditPlaceDescriptor();
    }

    public EditPlaceDescriptorBuilder(EditPlaceDescriptor descriptor) {
        this.descriptor = new EditPlaceDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPlaceDescriptor} with fields containing {@code place}'s details
     */
    public EditPlaceDescriptorBuilder(Place place) {
        descriptor = new EditPlaceDescriptor();
        descriptor.setName(place.getName());
        descriptor.setRating(place.getRating());
        descriptor.setDescription(place.getDescription());
        descriptor.setAddress(place.getAddress());
        descriptor.setTags(place.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPlaceDescriptor} that we are building.
     */
    public EditPlaceDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code EditPlaceDescriptor} that we are building.
     */
    public EditPlaceDescriptorBuilder withRating(String rating) {
        descriptor.setRating(new Rating(rating));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditPlaceDescriptor} that we are building.
     */
    public EditPlaceDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPlaceDescriptor} that we are building.
     */
    public EditPlaceDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPlaceDescriptor}
     * that we are building.
     */
    public EditPlaceDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPlaceDescriptor build() {
        return descriptor;
    }
}
