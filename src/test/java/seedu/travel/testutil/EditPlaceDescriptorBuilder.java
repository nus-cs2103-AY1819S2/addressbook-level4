package seedu.travel.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.travel.logic.commands.EditCommand.EditPlaceDescriptor;
import seedu.travel.model.place.Address;
import seedu.travel.model.place.CountryCode;
import seedu.travel.model.place.Description;
import seedu.travel.model.place.Name;
import seedu.travel.model.place.Place;
import seedu.travel.model.place.Rating;
import seedu.travel.model.tag.Tag;

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
        descriptor.setCountryCode(place.getCountryCode());
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
     * Sets the {@code CountryCode} of the {@code EditPlaceDescriptor} that we are building.
     */
    public EditPlaceDescriptorBuilder withCountryCode(String countryCode) {
        descriptor.setCountryCode(new CountryCode(countryCode));
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
