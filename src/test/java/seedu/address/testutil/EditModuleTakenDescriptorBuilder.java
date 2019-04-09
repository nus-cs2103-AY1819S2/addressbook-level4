package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditModuleTakenDescriptor;
import seedu.address.model.moduleinfo.ModuleInfoCode;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.Hour;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditModuleTakenDescriptor objects.
 */
public class EditModuleTakenDescriptorBuilder {

    private EditCommand.EditModuleTakenDescriptor descriptor;

    public EditModuleTakenDescriptorBuilder() {
        descriptor = new EditModuleTakenDescriptor();
    }

    public EditModuleTakenDescriptorBuilder(EditModuleTakenDescriptor descriptor) {
        this.descriptor = new EditModuleTakenDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleTakenDescriptor} with fields containing {@code moduleTaken}'s details
     */
    public EditModuleTakenDescriptorBuilder(ModuleTaken moduleTaken) {
        descriptor = new EditModuleTakenDescriptor();
        descriptor.setModuleInfoCode(moduleTaken.getModuleInfoCode());
        descriptor.setSemester(moduleTaken.getSemester());
        descriptor.setExpectedMinGrade(moduleTaken.getExpectedMinGrade());
        descriptor.setExpectedMaxGrade(moduleTaken.getExpectedMaxGrade());
        descriptor.setLectureHour(moduleTaken.getLectureHour());
        descriptor.setTags(moduleTaken.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditModuleTakenDescriptor} that we are building.
     */
    public EditModuleTakenDescriptorBuilder withName(String name) {
        descriptor.setModuleInfoCode(new ModuleInfoCode(name));
        return this;
    }

    /**
     * Sets the {@code Semester} of the {@code EditModuleTakenDescriptor} that we are building.
     */
    public EditModuleTakenDescriptorBuilder withSemester(String semester) {
        descriptor.setSemester(Semester.valueOf(semester));
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code EditModuleTakenDescriptor} that we are building.
     */
    public EditModuleTakenDescriptorBuilder withExpectedMinGrade(String expectedMinGrade) {
        descriptor.setExpectedMinGrade(Grade.valueOf(expectedMinGrade));
        return this;
    }

    /**
     * Sets the {@code Grade} of the {@code EditModuleTakenDescriptor} that we are building.
     */
    public EditModuleTakenDescriptorBuilder withExpectedMaxGrade(String expectedMaxGrade) {
        descriptor.setExpectedMaxGrade(Grade.valueOf(expectedMaxGrade));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditModuleTakenDescriptor} that we are building.
     */
    public EditModuleTakenDescriptorBuilder withLectureHour(String lectureHour) {
        descriptor.setLectureHour(new Hour(lectureHour));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditModuleTakenDescriptor}
     * that we are building.
     */
    public EditModuleTakenDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditCommand.EditModuleTakenDescriptor build() {
        return descriptor;
    }
}
