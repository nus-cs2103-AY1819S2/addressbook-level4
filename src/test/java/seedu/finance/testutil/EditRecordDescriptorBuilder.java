package seedu.finance.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.finance.logic.commands.EditCommand;
import seedu.finance.model.record.Amount;
import seedu.finance.model.record.Date;
import seedu.finance.model.record.Name;
import seedu.finance.model.record.Record;
import seedu.finance.model.tag.Tag;

/**
 * A utility class to help with building EditRecordDescriptor objects.
 */
public class EditRecordDescriptorBuilder {

    private EditCommand.EditRecordDescriptor descriptor;

    public EditRecordDescriptorBuilder() {
        descriptor = new EditCommand.EditRecordDescriptor();
    }

    public EditRecordDescriptorBuilder(EditCommand.EditRecordDescriptor descriptor) {
        this.descriptor = new EditCommand.EditRecordDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditRecordDescriptor} with fields containing {@code record}'s details
     */
    public EditRecordDescriptorBuilder(Record record) {
        descriptor = new EditCommand.EditRecordDescriptor();
        descriptor.setName(record.getName());
        descriptor.setAmount(record.getAmount());
        descriptor.setDate(record.getDate());
        descriptor.setTags(record.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditRecordDescriptor} that we are building.
     */
    public EditRecordDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Amount} of the {@code EditRecordDescriptor} that we are building.
     */
    public EditRecordDescriptorBuilder withAmount(String amount) {
        descriptor.setAmount(new Amount(amount));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditRecordDescriptor} that we are building.
     */
    public EditRecordDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditRecordDescriptor}
     * that we are building.
     */
    public EditRecordDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditRecordDescriptor build() {
        return descriptor;
    }
}
