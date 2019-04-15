package seedu.address.testutil;

import seedu.address.logic.commands.SetSemesterLimitCommand;
import seedu.address.model.limits.SemesterLimit;
import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.Hour;

/**
 * A utility class to help with building EditModuleTakenDescriptor objects.
 */
public class EditSemesterLimitDescriptorBuilder {

    private SetSemesterLimitCommand.EditSemesterLimitDescriptor descriptor;

    public EditSemesterLimitDescriptorBuilder() {
        descriptor = new SetSemesterLimitCommand.EditSemesterLimitDescriptor();
    }

    public EditSemesterLimitDescriptorBuilder(SetSemesterLimitCommand.EditSemesterLimitDescriptor descriptor) {
        this.descriptor = new SetSemesterLimitCommand.EditSemesterLimitDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleTakenDescriptor} with fields containing {@code moduleTaken}'s details
     */
    public EditSemesterLimitDescriptorBuilder(SemesterLimit semesterLimit) {
        descriptor = new SetSemesterLimitCommand.EditSemesterLimitDescriptor();
        descriptor.setMinCap(semesterLimit.getMinCap());
        descriptor.setMaxCap(semesterLimit.getMaxCap());
        descriptor.setMinLectureHour(semesterLimit.getMinLectureHour());
        descriptor.setMaxLectureHour(semesterLimit.getMaxLectureHour());
        descriptor.setMinTutorialHour(semesterLimit.getMinTutorialHour());
        descriptor.setMaxTutorialHour(semesterLimit.getMaxTutorialHour());
        descriptor.setMinLabHour(semesterLimit.getMinLabHour());
        descriptor.setMaxLabHour(semesterLimit.getMaxLabHour());
        descriptor.setMinProjectHour(semesterLimit.getMinProjectHour());
        descriptor.setMaxProjectHour(semesterLimit.getMaxProjectHour());
        descriptor.setMinPreparationHour(semesterLimit.getMinPreparationHour());
        descriptor.setMaxPreparationHour(semesterLimit.getMaxPreparationHour());
    }

    /**
     * Sets the {@code CapAverage} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemesterLimitDescriptorBuilder withMinCap(String cap) {
        descriptor.setMinCap(new CapAverage(Double.valueOf(cap)));
        return this;
    }

    /**
     * Sets the {@code CapAverage} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemesterLimitDescriptorBuilder withMaxCap(String cap) {
        descriptor.setMaxCap(new CapAverage(Double.valueOf(cap)));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemesterLimitDescriptorBuilder withMinLectureHour(String hour) {
        descriptor.setMinLectureHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemesterLimitDescriptorBuilder withMaxLectureHour(String hour) {
        descriptor.setMaxLectureHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemesterLimitDescriptorBuilder withMinTutorialHour(String hour) {
        descriptor.setMinTutorialHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemesterLimitDescriptorBuilder withMaxTutorialHour(String hour) {
        descriptor.setMaxTutorialHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemesterLimitDescriptorBuilder withMinLabHour(String hour) {
        descriptor.setMinLabHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemesterLimitDescriptorBuilder withMaxLabHour(String hour) {
        descriptor.setMaxLabHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemesterLimitDescriptorBuilder withMinProjectHour(String hour) {
        descriptor.setMinProjectHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemesterLimitDescriptorBuilder withMaxProjectHour(String hour) {
        descriptor.setMaxProjectHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemesterLimitDescriptorBuilder withMinPreparationHour(String hour) {
        descriptor.setMinPreparationHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemesterLimitDescriptorBuilder withMaxPreparationHour(String hour) {
        descriptor.setMaxPreparationHour(new Hour(hour));
        return this;
    }

    public SetSemesterLimitCommand.EditSemesterLimitDescriptor build() {
        return descriptor;
    }
}
