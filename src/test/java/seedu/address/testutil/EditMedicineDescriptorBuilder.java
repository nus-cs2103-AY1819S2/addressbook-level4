package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditMedicineDescriptor;
import seedu.address.model.medicine.Address;
import seedu.address.model.medicine.Email;
import seedu.address.model.medicine.Name;
import seedu.address.model.medicine.Medicine;
import seedu.address.model.medicine.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditMedicineDescriptor objects.
 */
public class EditMedicineDescriptorBuilder {

    private EditMedicineDescriptor descriptor;

    public EditMedicineDescriptorBuilder() {
        descriptor = new EditMedicineDescriptor();
    }

    public EditMedicineDescriptorBuilder(EditMedicineDescriptor descriptor) {
        this.descriptor = new EditMedicineDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMedicineDescriptor} with fields containing {@code medicine}'s details
     */
    public EditMedicineDescriptorBuilder(Medicine medicine) {
        descriptor = new EditMedicineDescriptor();
        descriptor.setName(medicine.getName());
        descriptor.setPhone(medicine.getPhone());
        descriptor.setEmail(medicine.getEmail());
        descriptor.setAddress(medicine.getAddress());
        descriptor.setTags(medicine.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditMedicineDescriptor} that we are building.
     */
    public EditMedicineDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditMedicineDescriptor} that we are building.
     */
    public EditMedicineDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditMedicineDescriptor} that we are building.
     */
    public EditMedicineDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditMedicineDescriptor} that we are building.
     */
    public EditMedicineDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditMedicineDescriptor}
     * that we are building.
     */
    public EditMedicineDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditMedicineDescriptor build() {
        return descriptor;
    }
}
