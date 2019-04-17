package seedu.address.testutil;

import seedu.address.logic.commands.WearCommand.UnavailablePersonDescriptor;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.Name;

/**
 * A utility class to help with building AvailablePersonDescriptor objects.
 */
public class UnavailableApparelDescriptorBuilder {

    private UnavailablePersonDescriptor descriptor;

    public UnavailableApparelDescriptorBuilder() {
        descriptor = new UnavailablePersonDescriptor();
    }

    public UnavailableApparelDescriptorBuilder(UnavailablePersonDescriptor descriptor) {
        this.descriptor = new UnavailablePersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code AvailablePersonDescriptor} with fields containing {@code apparel}'s details
     */
    public UnavailableApparelDescriptorBuilder(Apparel apparel) {
        descriptor = new UnavailablePersonDescriptor();
        descriptor.setName(apparel.getName());
        descriptor.setColor(apparel.getColor());
        descriptor.setClothingType(apparel.getClothingType());
    }

    /**
     * Sets the {@code Name} of the {@code AvailablePersonDescriptor} that we are building.
     */
    public UnavailableApparelDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Color} of the {@code AvailablePersonDescriptor} that we are building.
     */
    public UnavailableApparelDescriptorBuilder withColor(String color) {
        descriptor.setColor(new Color(color));
        return this;
    }

    /**
     * Sets the {@code ClothingType} of the {@code AvailablePersonDescriptor} that we are building.
     */
    public UnavailableApparelDescriptorBuilder withClothingType(String clothingType) {
        descriptor.setClothingType(new ClothingType(clothingType));
        return this;
    }

    public UnavailablePersonDescriptor build() {
        return descriptor;
    }
}
