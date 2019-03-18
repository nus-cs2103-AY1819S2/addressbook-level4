package seedu.hms.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

<<<<<<< HEAD:src/test/java/seedu/address/testutil/EditCustomerDescriptorBuilder.java
import seedu.address.logic.commands.EditCustomerCommand.EditCustomerDescriptor;
import seedu.address.model.customer.Address;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.DateOfBirth;
import seedu.address.model.customer.Email;
import seedu.address.model.customer.IdentificationNo;
import seedu.address.model.customer.Name;
import seedu.address.model.customer.Phone;
import seedu.address.model.tag.Tag;
=======
import seedu.hms.logic.commands.EditCustomerCommand.EditCustomerDescriptor;
import seedu.hms.model.customer.Address;
import seedu.hms.model.customer.Customer;
import seedu.hms.model.customer.Email;
import seedu.hms.model.customer.IdentificationNo;
import seedu.hms.model.customer.Name;
import seedu.hms.model.customer.Phone;
import seedu.hms.model.tag.Tag;
>>>>>>> f32e851bb9479d863dbfa54cb18c56bf0c85fbd6:src/test/java/seedu/hms/testutil/EditCustomerDescriptorBuilder.java

/**
 * A utility class to help with building EditCustomerDescriptor objects.
 */
public class EditCustomerDescriptorBuilder {

    private EditCustomerDescriptor descriptor;

    public EditCustomerDescriptorBuilder() {
        descriptor = new EditCustomerDescriptor();
    }

    public EditCustomerDescriptorBuilder(EditCustomerDescriptor descriptor) {
        this.descriptor = new EditCustomerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditCustomerDescriptor} with fields containing {@code customer}'s details
     */
    public EditCustomerDescriptorBuilder(Customer customer) {
        descriptor = new EditCustomerDescriptor();
        descriptor.setName(customer.getName());
        descriptor.setPhone(customer.getPhone());
        descriptor.setDateOfBirth(customer.getDateOfBirth());
        descriptor.setEmail(customer.getEmail());
        descriptor.setIdNum(customer.getIdNum());
        descriptor.setAddress(customer.getAddress());
        descriptor.setTags(customer.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code IdentificationNo} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withIdNum(String idnum) {
        descriptor.setIdNum(new IdentificationNo(idnum));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
<<<<<<< HEAD:src/test/java/seedu/address/testutil/EditCustomerDescriptorBuilder.java
     * Sets the {@code DateOfBirth} of the {@code EditCustomerDescriptor} that we are building.
     */
    public EditCustomerDescriptorBuilder withDateOfBirth(String dob) {
        descriptor.setDateOfBirth(new DateOfBirth(dob));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditCustomerDescriptor} that we are building.
=======
     * Sets the {@code hms} of the {@code EditCustomerDescriptor} that we are building.
>>>>>>> f32e851bb9479d863dbfa54cb18c56bf0c85fbd6:src/test/java/seedu/hms/testutil/EditCustomerDescriptorBuilder.java
     */
    public EditCustomerDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditCustomerDescriptor}
     * that we are building.
     */
    public EditCustomerDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCustomerDescriptor build() {
        return descriptor;
    }
}
