package seedu.address.testutil;

import seedu.address.logic.commands.WashCommand.AvailablePersonDescriptor;
import seedu.address.model.apparel.Apparel;
import seedu.address.model.apparel.ClothingType;
import seedu.address.model.apparel.Color;
import seedu.address.model.apparel.Name;

/**
 * A utility class to help with building AvailablePersonDescriptor objects.
 */
public class AvailableApparelDescriptorBuilder {

    private AvailablePersonDescriptor descriptor;

    public AvailableApparelDescriptorBuilder() {
        descriptor = new AvailablePersonDescriptor();
    }

    public AvailableApparelDescriptorBuilder(AvailablePersonDescriptor descriptor) {
        this.descriptor = new AvailablePersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code AvailablePersonDescriptor} with fields containing {@code apparel}'s details
     */
    public AvailableApparelDescriptorBuilder(Apparel apparel) {
        descriptor = new AvailablePersonDescriptor();
        descriptor.setName(apparel.getName());
        descriptor.setColor(apparel.getColor());
        descriptor.setClothingType(apparel.getClothingType());
    }

    /**
     * Sets the {@code Name} of the {@code AvailablePersonDescriptor} that we are building.
     */
    public AvailableApparelDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Color} of the {@code AvailablePersonDescriptor} that we are building.
     */
    public AvailableApparelDescriptorBuilder withColor(String color) {
        descriptor.setColor(new Color(color));
        return this;
    }

    /**
     * Sets the {@code ClothingType} of the {@code AvailablePersonDescriptor} that we are building.
     */
    public AvailableApparelDescriptorBuilder withClothingType(String clothingType) {
        descriptor.setClothingType(new ClothingType(clothingType));
        return this;
    }

    public AvailablePersonDescriptor build() {
        return descriptor;
    }
}
