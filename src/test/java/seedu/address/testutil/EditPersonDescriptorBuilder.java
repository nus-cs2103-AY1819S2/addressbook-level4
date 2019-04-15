package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Buyer;
import seedu.address.model.person.Email;
import seedu.address.model.person.Landlord;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.person.Seller;
import seedu.address.model.person.Tenant;
import seedu.address.model.property.Price;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setRemark(person.getRemark());
        if (person instanceof Seller) {
            Seller seller = (Seller) person;
            descriptor.setAddress(seller.getAddress());
            descriptor.setSellingPrice(seller.getSellingPrice());
            descriptor.setTags(seller.getTags());
        }
        if (person instanceof Landlord) {
            Landlord landlord = (Landlord) person;
            descriptor.setAddress(landlord.getAddress());
            descriptor.setRentalPrice(landlord.getRentalPrice());
            descriptor.setTags(landlord.getTags());
        }
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code buyer}'s details
     */
    public EditPersonDescriptorBuilder(Buyer buyer) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(buyer.getName());
        descriptor.setPhone(buyer.getPhone());
        descriptor.setEmail(buyer.getEmail());
        descriptor.setRemark(buyer.getRemark());
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code tenant}'s details
     */
    public EditPersonDescriptorBuilder(Tenant tenant) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(tenant.getName());
        descriptor.setPhone(tenant.getPhone());
        descriptor.setEmail(tenant.getEmail());
        descriptor.setRemark(tenant.getRemark());
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code seller}'s details
     */
    public EditPersonDescriptorBuilder(Seller seller) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(seller.getName());
        descriptor.setPhone(seller.getPhone());
        descriptor.setEmail(seller.getEmail());
        descriptor.setRemark(seller.getRemark());
        descriptor.setAddress(seller.getAddress());
        descriptor.setSellingPrice(seller.getSellingPrice());
        descriptor.setTags(seller.getTags());
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code landlord}'s details
     */
    public EditPersonDescriptorBuilder(Landlord landlord) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(landlord.getName());
        descriptor.setPhone(landlord.getPhone());
        descriptor.setEmail(landlord.getEmail());
        descriptor.setRemark(landlord.getRemark());
        descriptor.setAddress(landlord.getAddress());
        descriptor.setRentalPrice(landlord.getRentalPrice());
        descriptor.setTags(landlord.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(new Remark(remark));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code SellingPrice} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withSellingPrice(String sellingPrice) {
        descriptor.setSellingPrice(new Price(sellingPrice));
        return this;
    }

    /**
     * Sets the {@code RentalPrice} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withRentalPrice(String rentalPrice) {
        descriptor.setRentalPrice(new Price(rentalPrice));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
