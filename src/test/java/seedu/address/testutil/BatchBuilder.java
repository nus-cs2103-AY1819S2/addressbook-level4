package seedu.address.testutil;

import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Quantity;

/**
 * A utility class to help with building Batch objects.
 */
public class BatchBuilder {

    public static final String DEFAULT_BATCH_NUMBER = "NDC 0777-3105-02";
    public static final String DEFAULT_QUANTITY = "500";
    public static final String DEFAULT_EXPIRY = "12/12/2019";

    private BatchNumber batchNumber;
    private Quantity quantity;
    private Expiry expiry;

    public BatchBuilder() {
        batchNumber = new BatchNumber(DEFAULT_BATCH_NUMBER);
        quantity = new Quantity(DEFAULT_QUANTITY);
        expiry = new Expiry(DEFAULT_EXPIRY);
    }

    /**
     * Initializes the BatchBuilder with the data of {@code batchToCopy}.
     */
    public BatchBuilder(Batch batchToCopy) {
        batchNumber = batchToCopy.getBatchNumber();
        quantity = batchToCopy.getQuantity();
        expiry = batchToCopy.getExpiry();
    }

    /**
     * Sets the {@code BatchNumber} of the {@code Batch} that we are building.
     */
    public BatchBuilder withBatchNumber(String batchNumber) {
        this.batchNumber = new BatchNumber(batchNumber);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Batch} that we are building.
     */
    public BatchBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Expiry} of the {@code Medicine} that we are building.
     */
    public BatchBuilder withExpiry(String expiry) {
        this.expiry = new Expiry(expiry);
        return this;
    }

    public Batch build() {
        return new Batch(batchNumber, quantity, expiry);
    }

}

