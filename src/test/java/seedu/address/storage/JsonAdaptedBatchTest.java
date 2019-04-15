package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.JsonAdaptedBatch.MISSING_FIELD_MESSAGE_FORMAT;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.medicine.BatchNumber;
import seedu.address.model.medicine.Expiry;
import seedu.address.model.medicine.Quantity;
import seedu.address.testutil.Assert;
import seedu.address.testutil.BatchBuilder;

public class JsonAdaptedBatchTest {
    private static final String INVALID_BATCHNUMBER = "HASF@#!@#";
    private static final String INVALID_QUANTITY = "+651234";
    private static final String INVALID_EXPIRY = "a/1/09";

    private static final String VALID_BATCHNUMBER = BatchBuilder.DEFAULT_BATCH_NUMBER;
    private static final String VALID_QUANTITY = BatchBuilder.DEFAULT_QUANTITY;
    private static final String VALID_EXPIRY = BatchBuilder.DEFAULT_EXPIRY;

    @Test
    public void toModelType_validBatchDetails_returnsMedicine() throws Exception {

        JsonAdaptedBatch batch = new JsonAdaptedBatch(new BatchBuilder().build());
        assertEquals(new BatchBuilder().build(), batch.toModelType());
    }

    @Test
    public void toModelType_invalidBatchNumber_throwsIllegalValueException() {
        JsonAdaptedBatch batch = new JsonAdaptedBatch(INVALID_BATCHNUMBER, VALID_QUANTITY, VALID_EXPIRY);
        String expectedMessage = BatchNumber.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, batch::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedBatch batch = new JsonAdaptedBatch(null, VALID_QUANTITY, VALID_EXPIRY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, BatchNumber.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, batch::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedBatch batch = new JsonAdaptedBatch(VALID_BATCHNUMBER, INVALID_QUANTITY, VALID_EXPIRY);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, batch::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        JsonAdaptedBatch batch = new JsonAdaptedBatch(VALID_BATCHNUMBER, null, VALID_EXPIRY);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, batch::toModelType);
    }

    @Test
    public void toModelType_invalidExpiry_throwsIllegalValueException() {
        JsonAdaptedBatch batch = new JsonAdaptedBatch(VALID_BATCHNUMBER, VALID_QUANTITY, INVALID_EXPIRY);
        String expectedMessage = Expiry.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, batch::toModelType);
    }

    @Test
    public void toModelType_nullExpiry_throwsIllegalValueException() {
        JsonAdaptedBatch batch = new JsonAdaptedBatch(VALID_BATCHNUMBER, VALID_QUANTITY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Expiry.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, batch::toModelType);
    }

}
