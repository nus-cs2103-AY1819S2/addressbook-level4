package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.Batch;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Quantity;

/**
 * Jackson-friendly version of {@link Batch}.
 */
class JsonAdaptedBatch {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Batches' %s field is missing!";

    private final String batchNumber;
    private final String quantity;
    private final String expiry;

    /**
     * Constructs a {@code JsonAdaptedBatch} with the given batch details.
     */
    @JsonCreator
    public JsonAdaptedBatch(@JsonProperty("batchNumber") String batchNumber, @JsonProperty("quantity") String quantity,
            @JsonProperty("expiry") String expiry) {
        this.batchNumber = batchNumber;
        this.quantity = quantity;
        this.expiry = expiry;
    }

    /**
     * Converts a given {@code Batch} into this class for Jackson use.
     */
    public JsonAdaptedBatch(Batch source) {
        batchNumber = source.getBatchNumber().batchNumber;
        quantity = source.getQuantity().toString();
        expiry = source.getExpiry().toString();
    }

    /**
     * Converts this Jackson-friendly adapted batch object into the model's {@code Medicine} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted medicine.
     */
    public Batch toModelType() throws IllegalValueException {
        if (batchNumber == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    BatchNumber.class.getSimpleName()));
        }
        if (!BatchNumber.isValidBatchNumber(batchNumber)) {
            throw new IllegalValueException(BatchNumber.MESSAGE_CONSTRAINTS);
        }
        final BatchNumber modelBatchNumber = new BatchNumber(batchNumber);

        if (quantity == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (expiry == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Expiry.class.getSimpleName()));
        }
        if (!Expiry.isValidDate(expiry)) {
            throw new IllegalValueException(Expiry.MESSAGE_CONSTRAINTS);
        }
        final Expiry modelExpiry = new Expiry(expiry);

        return new Batch(modelBatchNumber, modelQuantity, modelExpiry);
    }

}
