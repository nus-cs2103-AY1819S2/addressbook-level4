package seedu.equipment.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.equipment.logic.commands.EditCommand.EditEquipmentDescriptor;
import seedu.equipment.model.equipment.Address;
import seedu.equipment.model.equipment.Date;
import seedu.equipment.model.equipment.Equipment;
import seedu.equipment.model.equipment.Name;
import seedu.equipment.model.equipment.Phone;
import seedu.equipment.model.equipment.SerialNumber;
import seedu.equipment.model.tag.Tag;

/**
 * A utility class to help with building EditEquipmentDescriptor objects.
 */
public class EditEquipmentDescriptorBuilder {

    private EditEquipmentDescriptor descriptor;

    public EditEquipmentDescriptorBuilder() {
        descriptor = new EditEquipmentDescriptor();
    }

    public EditEquipmentDescriptorBuilder(EditEquipmentDescriptor descriptor) {
        this.descriptor = new EditEquipmentDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEquipmentDescriptor} with fields containing {@code equipment}'s details
     */
    public EditEquipmentDescriptorBuilder(Equipment equipment) {
        descriptor = new EditEquipmentDescriptor();
        descriptor.setName(equipment.getName());
        descriptor.setPhone(equipment.getPhone());
        descriptor.setDate(equipment.getDate());
        descriptor.setAddress(equipment.getAddress());
        descriptor.setSerialNumber(equipment.getSerialNumber());
        descriptor.setTags(equipment.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditEquipmentDescriptor} that we are building.
     */
    public EditEquipmentDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditEquipmentDescriptor} that we are building.
     */
    public EditEquipmentDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditEquipmentDescriptor} that we are building.
     */
    public EditEquipmentDescriptorBuilder withEmail(String email) {
        descriptor.setDate(new Date(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditEquipmentDescriptor} that we are building.
     */
    public EditEquipmentDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code SerialNumber} of the {@code EditEquipmentDescriptor} that we are building.
     */
    public EditEquipmentDescriptorBuilder withSerialNumber(String serialNumber) {
        descriptor.setSerialNumber(new SerialNumber(serialNumber));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditEquipmentDescriptor}
     * that we are building.
     */
    public EditEquipmentDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditEquipmentDescriptor build() {
        return descriptor;
    }
}
