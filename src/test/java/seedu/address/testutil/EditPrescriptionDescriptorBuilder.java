package seedu.address.testutil;

import seedu.address.logic.commands.prescription.EditPrescriptionCommand;
import seedu.address.model.prescription.Description;
import seedu.address.model.prescription.Prescription;

/**
 * A utility class to help with building EditPatientDescriptor objects.
 */
public class EditPrescriptionDescriptorBuilder {

    private EditPrescriptionCommand.EditPrescriptionDescriptor descriptor;

    public EditPrescriptionDescriptorBuilder() {
        descriptor = new EditPrescriptionCommand.EditPrescriptionDescriptor();
    }

    public EditPrescriptionDescriptorBuilder(EditPrescriptionCommand.EditPrescriptionDescriptor descriptor) {
        this.descriptor = new EditPrescriptionCommand.EditPrescriptionDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPrescriptionDescriptor} with fields containing {@code prescription}'s details
     */
    public EditPrescriptionDescriptorBuilder(Prescription prescription) {
        descriptor = new EditPrescriptionCommand.EditPrescriptionDescriptor();
        descriptor.setDescription(prescription.getDescription());
    }

    /**
     * Sets the {@code WriteUp} of the {@code EditMedHistDescriptor} that we are building.
     */
    public EditPrescriptionDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    public EditPrescriptionCommand.EditPrescriptionDescriptor build() {
        return descriptor;
    }
}
