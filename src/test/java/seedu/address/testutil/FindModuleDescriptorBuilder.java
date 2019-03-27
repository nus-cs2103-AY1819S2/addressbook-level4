package seedu.address.testutil;

import seedu.address.logic.commands.FindCommand.FindModuleDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.moduletaken.Grade;
import seedu.address.model.moduletaken.ModuleTaken;
import seedu.address.model.moduletaken.Semester;

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
    public FindModuleDescriptorBuilder(ModuleTaken module) {
        descriptor = new FindModuleDescriptor();
        descriptor.setSubCode(module.getModuleInfoCode().toString());
        descriptor.setSemester(module.getSemester());
        descriptor.setGrade(module.getExpectedMinGrade());
        descriptor.setFinished(module.isFinished(Semester.Y1S1));
    }

    /**
     * Sets the {@code subCode} of the {@code FindModuleDescriptor} that we are building.
     */
    public FindModuleDescriptorBuilder withCode(String subCode) {
        descriptor.setSubCode(subCode);
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

    /**
     * Sets the {@code isFinished} of the {@code FindModuleDescriptor} that we are building.
     */
    public FindModuleDescriptorBuilder withFinishedStatus(String finishedStatus) {
        descriptor.setFinished(ParserUtil.parseFinishedStatus(finishedStatus));
        return this;
    }

    public FindModuleDescriptor build() {
        return descriptor;
    }
}
