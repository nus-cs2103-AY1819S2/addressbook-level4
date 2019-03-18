package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.request.EditRequestCommand.EditRequestDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestDate;
import seedu.address.model.tag.ConditionTag;

/**
 * A utility class to help with building EditRequestDescriptor objects
 */
public class EditRequestDescriptorBuilder {
    private EditRequestDescriptor descriptor;

    public EditRequestDescriptorBuilder() {
        descriptor = new EditRequestDescriptor();
    }

    public EditRequestDescriptorBuilder(EditRequestDescriptor descriptor) {
        this.descriptor = new EditRequestDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRequestDescriptor} with fields containing {@code order}'s details
     */
    public EditRequestDescriptorBuilder(Request request) {
        descriptor = new EditRequestDescriptor();
        descriptor.setName(request.getPatient().getName());
        descriptor.setPhone(request.getPatient().getPhone());
        descriptor.setDate(request.getRequestDate());
        descriptor.setConditions(request.getConditions().getConditions());
    }

    /**
     * Sets the {@code Name} of the {@code EditRequestDescriptor} that we are building.
     */
    public EditRequestDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditRequestDescriptor} that we are building
     */
    public EditRequestDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditRequestDescriptor} that we are building
     */
    public EditRequestDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditRequestDescriptor} that we are building.
     */
    public EditRequestDescriptorBuilder withDate(String date) {
        descriptor.setDate(new RequestDate(date));
        return this;
    }

    /**
     * Parses the {@code conditions} into a {@code Set<Tag>} and set it to the {@code
     * EditOrderDescriptor} that we are building
     */
    public EditRequestDescriptorBuilder withConditions(String... conditions) {
        Set<ConditionTag> conditionSet =
            Stream.of(conditions).map(ConditionTag::new).collect(Collectors.toSet());
        descriptor.setConditions(conditionSet);
        return this;
    }

    public EditRequestDescriptor build() {
        return descriptor;
    }

}
