package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
//import seedu.address.model.pdf.Address;
//import seedu.address.model.pdf.Directory;
//import seedu.address.model.pdf.Email;
import seedu.address.model.pdf.Name;
import seedu.address.model.pdf.Pdf;
//import seedu.address.model.pdf.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPdfDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPdfDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPdfDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code pdf}'s details
     */
    public EditPdfDescriptorBuilder(Pdf pdf) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(pdf.getName());
        descriptor.setTags(pdf.getTags());
        /*descriptor.setDirectory(pdf.getDirectory());
        descriptor.setPhone(pdf.getPhone());
        descriptor.setEmail(pdf.getEmail());
        descriptor.setAddress(pdf.getAddress());*/
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPdfDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }


    //    /**
    //     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
    //     */
    //    public EditPdfDescriptorBuilder withPhone(String phone) {
    //        descriptor.setPhone(new Phone(phone));
    //        return this;
    //    }
    //
    //    /**
    //     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
    //     */
    //    public EditPdfDescriptorBuilder withEmail(String email) {
    //        descriptor.setEmail(new Email(email));
    //        return this;
    //    }
    //
    //    /**
    //     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
    //     */
    //    public EditPdfDescriptorBuilder withAddress(String address) {
    //        descriptor.setAddress(new Address(address));
    //        return this;
    //    }
    //
    //    /**
    //     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
    //     */
    //    public EditPdfDescriptorBuilder withDirectory(String directory) {
    //        descriptor.setDirectory(new Directory(directory));
    //        return this;
    //    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPdfDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
