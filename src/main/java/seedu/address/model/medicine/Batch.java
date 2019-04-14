package seedu.address.model.medicine;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a batch of medicine.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Batch {
    private final BatchNumber batchNumber;
    private final Quantity quantity;
    private final Expiry expiry;

    /**
     * Constructs a {@code Batch}.
     */
    public Batch(BatchNumber batchNumber, Quantity quantity, Expiry expiry) {
        requireAllNonNull(batchNumber, expiry, quantity);
        this.batchNumber = batchNumber;
        this.quantity = quantity;
        this.expiry = expiry;
    }

    public BatchNumber getBatchNumber() {
        return batchNumber;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Expiry getExpiry() {
        return expiry;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Batch // instanceof handles nulls
                && batchNumber.equals(((Batch) other).batchNumber)
                && quantity.equals(((Batch) other).quantity))
                && expiry.equals(((Batch) other).expiry); // state check
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchNumber, quantity, expiry);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Batch No.: ")
                .append(getBatchNumber())
                .append(" Quantity: ")
                .append(getQuantity())
                .append(" Expiry: ")
                .append(getExpiry());
        return builder.toString();
    }
}
