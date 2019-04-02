package seedu.address.model.medicine;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a batch of medicine.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Batch {
    private final BatchNumber batchNumber;
    private final Expiry expiry;
    private final Quantity quantity;

    /**
     * Constructs a {@code Batch}.
     */
    public Batch(BatchNumber batchNumber, Expiry expiry, Quantity quantity) {
        requireAllNonNull(batchNumber, expiry, quantity);
        this.batchNumber = batchNumber;
        this.expiry = expiry;
        this.quantity = quantity;
    }

    public BatchNumber getBatchNumber() {
        return batchNumber;
    }

    public Expiry getExpiry() {
        return expiry;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public boolean hasExpiry() {
        return expiry.getExpiryDate() != null;
    }

    public boolean hasNonZeroQuantity() {
        return quantity != null && !quantity.value.equals("0");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.medicine.Batch // instanceof handles nulls
                && batchNumber.equals(((seedu.address.model.medicine.Batch) other).batchNumber))
                && expiry.equals(((seedu.address.model.medicine.Batch) other).expiry)
                && quantity.equals(((seedu.address.model.medicine.Batch) other).quantity); // state check
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
