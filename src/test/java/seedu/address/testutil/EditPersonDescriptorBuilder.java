package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.Hour;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;
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
     * Returns an {@code EditPersonDescriptor} with fields containing {@code moduleTaken}'s details
     */
    public EditPersonDescriptorBuilder(ModuleTaken moduleTaken) {
        descriptor = new EditPersonDescriptor();
        descriptor.setModuleInfoCode(moduleTaken.getModuleInfo());
        descriptor.setSemester(moduleTaken.getSemester());
        descriptor.setExpectedMinGrade(moduleTaken.getExpectedMinGrade());
        descriptor.setExpectedMaxGrade(moduleTaken.getExpectedMaxGrade());
        descriptor.setLectureHour(moduleTaken.getLectureHour());
        descriptor.setTags(moduleTaken.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setModuleInfoCode(new ModuleInfoCode(name));
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withSemester(String semester) {
        descriptor.setSemester(Semester.valueOf(semester));
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withExpectedMinGrade(String expectedMinGrade) {
        descriptor.setExpectedMinGrade(Grade.valueOf(expectedMinGrade));
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withExpectedMaxGrade(String expectedMaxGrade) {
        descriptor.setExpectedMaxGrade(Grade.valueOf(expectedMaxGrade));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withLectureHour(String lectureHour) {
        descriptor.setLectureHour(new Hour(lectureHour));
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
