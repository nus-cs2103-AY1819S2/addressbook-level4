package seedu.pdf.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.pdf.logic.commands.RenameCommand;
import seedu.pdf.model.pdf.Name;
import seedu.pdf.model.pdf.Pdf;
import seedu.pdf.model.pdf.Size;
import seedu.pdf.model.tag.Tag;

/**
 * A utility class to help with building EditPdfDescriptor objects.
 */
public class EditPdfDescriptorBuilder {

    private RenameCommand.EditPdfDescriptor descriptor;

    public EditPdfDescriptorBuilder() {
        descriptor = new RenameCommand.EditPdfDescriptor();
    }

    public EditPdfDescriptorBuilder(RenameCommand.EditPdfDescriptor descriptor) {
        this.descriptor = new RenameCommand.EditPdfDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPdfDescriptor} with fields containing {@code pdf}'s details
     */
    public EditPdfDescriptorBuilder(Pdf pdf) {
        descriptor = new RenameCommand.EditPdfDescriptor();
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

    public RenameCommand.EditPdfDescriptor build() {
        return descriptor;
    }
}
