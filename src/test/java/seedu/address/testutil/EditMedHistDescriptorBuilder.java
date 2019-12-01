package seedu.address.testutil;

import seedu.address.logic.commands.medicalhistory.EditMedHistCommand;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.medicalhistory.WriteUp;

/**
 * A utility class to help with building EditPatientDescriptor objects.
 */
public class EditMedHistDescriptorBuilder {

    private EditMedHistCommand.EditMedHistDescriptor descriptor;

    public EditMedHistDescriptorBuilder() {
        descriptor = new EditMedHistCommand.EditMedHistDescriptor();
    }

    public EditMedHistDescriptorBuilder(EditMedHistCommand.EditMedHistDescriptor descriptor) {
        this.descriptor = new EditMedHistCommand.EditMedHistDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMedHistDescriptor} with fields containing {@code medical history}'s details
     */
    public EditMedHistDescriptorBuilder(MedicalHistory medHist) {
        descriptor = new EditMedHistCommand.EditMedHistDescriptor();
        descriptor.setWriteUp(medHist.getWriteUp());
    }

    /**
     * Sets the {@code WriteUp} of the {@code EditMedHistDescriptor} that we are building.
     */
    public EditMedHistDescriptorBuilder withWriteUp(String writeUp) {
        descriptor.setWriteUp(new WriteUp(writeUp));
        return this;
    }

    public EditMedHistCommand.EditMedHistDescriptor build() {
        return descriptor;
    }
}
