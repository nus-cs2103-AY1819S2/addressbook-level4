package seedu.address.testutil;

import seedu.address.logic.commands.SetSemLimitCommand;
import seedu.address.logic.commands.SetSemLimitCommand.EditSemLimitDescriptor;
import seedu.address.model.SemLimit;
import seedu.address.model.moduletaken.CapAverage;
import seedu.address.model.moduletaken.Hour;

/**
 * A utility class to help with building EditModuleTakenDescriptor objects.
 */
public class EditSemLimitDescriptorBuilder {

    private EditSemLimitDescriptor descriptor;

    public EditSemLimitDescriptorBuilder() {
        descriptor = new SetSemLimitCommand.EditSemLimitDescriptor();
    }

    public EditSemLimitDescriptorBuilder(EditSemLimitDescriptor descriptor) {
        this.descriptor = new EditSemLimitDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleTakenDescriptor} with fields containing {@code moduleTaken}'s details
     */
    public EditSemLimitDescriptorBuilder(SemLimit semLimit) {
        descriptor = new EditSemLimitDescriptor();
        descriptor.setMinCap(semLimit.getMinCap());
        descriptor.setMaxCap(semLimit.getMaxCap());
        descriptor.setMinLectureHour(semLimit.getMinLectureHour());
        descriptor.setMaxLectureHour(semLimit.getMaxLectureHour());
        descriptor.setMinTutorialHour(semLimit.getMinTutorialHour());
        descriptor.setMaxTutorialHour(semLimit.getMaxTutorialHour());
        descriptor.setMinLabHour(semLimit.getMinLabHour());
        descriptor.setMaxLabHour(semLimit.getMaxLabHour());
        descriptor.setMinProjectHour(semLimit.getMinProjectHour());
        descriptor.setMaxProjectHour(semLimit.getMaxProjectHour());
        descriptor.setMinPreparationHour(semLimit.getMinPreparationHour());
        descriptor.setMaxPreparationHour(semLimit.getMaxPreparationHour());
    }

    /**
     * Sets the {@code CapAverage} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemLimitDescriptorBuilder withMinCap(String cap) {
        descriptor.setMinCap(new CapAverage(Double.valueOf(cap)));
        return this;
    }

    /**
     * Sets the {@code CapAverage} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemLimitDescriptorBuilder withMaxCap(String cap) {
        descriptor.setMaxCap(new CapAverage(Double.valueOf(cap)));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemLimitDescriptorBuilder withMinLectureHour(String hour) {
        descriptor.setMinLectureHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemLimitDescriptorBuilder withMaxLectureHour(String hour) {
        descriptor.setMaxLectureHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemLimitDescriptorBuilder withMinTutorialHour(String hour) {
        descriptor.setMinTutorialHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemLimitDescriptorBuilder withMaxTutorialHour(String hour) {
        descriptor.setMaxTutorialHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemLimitDescriptorBuilder withMinLabHour(String hour) {
        descriptor.setMinLabHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemLimitDescriptorBuilder withMaxLabHour(String hour) {
        descriptor.setMaxLabHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemLimitDescriptorBuilder withMinProjectHour(String hour) {
        descriptor.setMinProjectHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemLimitDescriptorBuilder withMaxProjectHour(String hour) {
        descriptor.setMaxProjectHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemLimitDescriptorBuilder withMinPreparationHour(String hour) {
        descriptor.setMinPreparationHour(new Hour(hour));
        return this;
    }

    /**
     * Sets the {@code Hour} of the {@code EditSemesterLimitDescriptor} that we are building.
     */
    public EditSemLimitDescriptorBuilder withMaxPreparationHour(String hour) {
        descriptor.setMaxPreparationHour(new Hour(hour));
        return this;
    }

    public EditSemLimitDescriptor build() {
        return descriptor;
    }
}
