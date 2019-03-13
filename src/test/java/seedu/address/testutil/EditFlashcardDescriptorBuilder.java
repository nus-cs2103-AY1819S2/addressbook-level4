package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.address.model.flashcard.Face;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditFlashcardDescriptor objects.
 */
public class EditFlashcardDescriptorBuilder {

    private EditCommand.EditFlashcardDescriptor descriptor;

    public EditFlashcardDescriptorBuilder() {
        descriptor = new EditFlashcardDescriptor();
    }

    public EditFlashcardDescriptorBuilder(EditFlashcardDescriptor descriptor) {
        this.descriptor = new EditCommand.EditFlashcardDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditFlashcardDescriptor} with fields containing {@code flashcard}'s details
     */
    public EditFlashcardDescriptorBuilder(Flashcard flashcard) {
        descriptor = new EditFlashcardDescriptor();
        descriptor.setFrontFace(flashcard.getFrontFace());
        descriptor.setBackFace(flashcard.getBackFace());
        descriptor.setTags(flashcard.getTags());
    }

    /**
     * Sets the {@code FrontFace} of the {@code EditFlashcardDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withFrontFace(String frontFace) {
        descriptor.setFrontFace(new Face(frontFace));
        return this;
    }

    /**
     * Sets the {@code BackFace} of the {@code EditFlashcardDescriptor} that we are building.
     */
    public EditFlashcardDescriptorBuilder withBackFace(String backFace) {
        descriptor.setBackFace(new Face(backFace));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditFlashcardDescriptor}
     * that we are building.
     */
    public EditFlashcardDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditFlashcardDescriptor build() {
        return descriptor;
    }
}
