package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditBookCommand.EditBookDescriptor;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.BookName;
import seedu.address.model.book.Rating;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditBookDescriptor objects.
 */
public class EditBookDescriptorBuilder {

    private EditBookDescriptor descriptor;

    public EditBookDescriptorBuilder() {
        descriptor = new EditBookDescriptor();
    }

    public EditBookDescriptorBuilder(EditBookDescriptor descriptor) {
        this.descriptor = new EditBookDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBookDescriptor} with fields containing {@code book}'s details
     */
    public EditBookDescriptorBuilder(Book book) {
        descriptor = new EditBookDescriptor();
        descriptor.setName(book.getBookName());
        descriptor.setRating(book.getRating());
        descriptor.setAuthor(book.getAuthor());
        descriptor.setTags(book.getTags());
    }

    /**
     * Sets the {@code BookName} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withBookName(String name) {
        descriptor.setName(new BookName(name));
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withRating(String rating) {
        descriptor.setRating(new Rating(rating));
        return this;
    }

    /**
     * Sets the {@code Author} of the {@code EditBookDescriptor} that we are building.
     */
    public EditBookDescriptorBuilder withAuthor(String author) {
        descriptor.setAuthor(new Author(author));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditBookDescriptor}
     * that we are building.
     */
    public EditBookDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditBookDescriptor build() {
        return descriptor;
    }
}
