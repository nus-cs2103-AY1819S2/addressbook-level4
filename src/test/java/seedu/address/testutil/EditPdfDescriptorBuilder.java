package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.model.pdf.Name;
import seedu.address.model.pdf.Pdf;
import seedu.address.model.pdf.Size;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPdfDescriptor objects.
 */
public class EditPdfDescriptorBuilder {

    private EditCommand.EditPdfDescriptor descriptor;

    public EditPdfDescriptorBuilder() {
        descriptor = new EditCommand.EditPdfDescriptor();
    }

    public EditPdfDescriptorBuilder(EditCommand.EditPdfDescriptor descriptor) {
        this.descriptor = new EditCommand.EditPdfDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPdfDescriptor} with fields containing {@code pdf}'s details
     */
    public EditPdfDescriptorBuilder(Pdf pdf) {
        descriptor = new EditCommand.EditPdfDescriptor();
        descriptor.setName(pdf.getName());
        descriptor.setTags(pdf.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPdfDescriptor} that we are building.
     */
    public EditPdfDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditPdfDescriptor} that we are building.
     */
    public EditPdfDescriptorBuilder withSize(String size) {
        descriptor.setSize(new Size(size));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPdfDescriptor}
     * that we are building.
     */
    public EditPdfDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditPdfDescriptor build() {
        return descriptor;
    }
}
