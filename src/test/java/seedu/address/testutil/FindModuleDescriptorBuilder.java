package seedu.address.testutil;

import seedu.address.logic.commands.FindCommand.FindModuleDescriptor;
import seedu.address.model.person.Grade;
import seedu.address.model.person.Person;
import seedu.address.model.person.Semester;

/**
 * A utility class to help with building FindModuleDescriptor objects.
 */
public class FindModuleDescriptorBuilder {

    private FindModuleDescriptor descriptor;

    public FindModuleDescriptorBuilder() {
        descriptor = new FindModuleDescriptor();
    }

    public FindModuleDescriptorBuilder(FindModuleDescriptor descriptor) {
        this.descriptor = new FindModuleDescriptor(descriptor);
    }

    /**
     * Returns an {@code FindModuleDescriptor} with fields containing some of {@code module}'s details
     */
    public FindModuleDescriptorBuilder(Person module) {
        descriptor = new FindModuleDescriptor();
        descriptor.setCode(module.getModuleInfo().fullName);
        descriptor.setSemester(module.getSemester());
        descriptor.setGrade(module.getExpectedMinGrade());
    }

    /**
     * Sets the {@code code} of the {@code FindModuleDescriptor} that we are building.
     */
    public FindModuleDescriptorBuilder withCode(String code) {
        descriptor.setCode(code);
        return this;
    }

    /**
     * Sets the {@code semester} of the {@code FindModuleDescriptor} that we are building.
     */
    public FindModuleDescriptorBuilder withSemester(String semester) {
        descriptor.setSemester(Semester.valueOf(semester));
        return this;
    }

    /**
     * Sets the {@code grade} of the {@code FindModuleDescriptor} that we are building.
     */
    public FindModuleDescriptorBuilder withGrade(String grade) {
        descriptor.setGrade(Grade.valueOf(grade));
        return this;
    }

    public FindModuleDescriptor build() {
        return descriptor;
    }
}
