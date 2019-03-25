package seedu.address.testutil;

import seedu.address.logic.commands.UpdateCommand.UpdateBatchDescriptor;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Quantity;

/**
 * A utility class to help with building UpdateBatchDescriptor objects.
 */
public class UpdateBatchDescriptorBuilder {

    private UpdateBatchDescriptor descriptor;

    public UpdateBatchDescriptorBuilder() {
        descriptor = new UpdateBatchDescriptor();
    }

    public UpdateBatchDescriptorBuilder(UpdateBatchDescriptor descriptor) {
        this.descriptor = new UpdateBatchDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdateBatchDescriptorBuilder} with fields containing {@code batch}'s details
     */
    public UpdateBatchDescriptorBuilder(Batch batch) {
        descriptor = new UpdateBatchDescriptor();
        descriptor.setBatchNumber(batch.getBatchNumber());
        descriptor.setQuantity(batch.getQuantity());
        descriptor.setExpiry(batch.getExpiry());
    }

    /**
     * Sets the {@code BatchNumber} of the {@code UpdateBatchDescriptor} that we are building.
     */
    public UpdateBatchDescriptorBuilder withBatchNumber(String batchNumber) {
        descriptor.setBatchNumber(new BatchNumber(batchNumber));
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code UpdateBatchDescriptor} that we are building.
     */
    public UpdateBatchDescriptorBuilder withQuantity(String quantity) {
        descriptor.setQuantity(new Quantity(quantity));
        return this;
    }

    /**
     * Sets the {@code Expiry} of the {@code UpdateBatchDescriptor} that we are building.
     */
    public UpdateBatchDescriptorBuilder withExpiry(String expiry) {
        descriptor.setExpiry(new Expiry(expiry));
        return this;
    }

    /**
     * Sets the {@code Expiry} of the {@code UpdateBatchDescriptor} that we are building to null.
     */
    public UpdateBatchDescriptorBuilder withNoExpiry() {
        descriptor.setExpiry(null);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code UpdateBatchDescriptor} that we are building to null.
     */
    public UpdateBatchDescriptorBuilder withNoQuantity() {
        descriptor.setQuantity(null);
        return this;
    }

    public UpdateBatchDescriptor build() {
        return descriptor;
    }
}

