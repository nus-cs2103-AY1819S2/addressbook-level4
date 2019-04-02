package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BATCHNUMBER_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EXPIRY_AMOXICILLIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUANTITY_AMOXICILLIN;

import org.junit.Test;

import seedu.address.testutil.Assert;
import seedu.address.testutil.BatchBuilder;

public class BatchTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Batch(null, null, null));
    }

    @Test
    public void constructor_invalidBatchNumber_throwsIllegalArgumentException() {
        String invalidBatchNumber = " ";
        Assert.assertThrows(IllegalArgumentException.class, () ->
            new BatchBuilder().withBatchNumber(invalidBatchNumber));
    }

    @Test
    public void constructor_invalidExpiry_throwsIllegalArgumentException() {
        String invalidExpiry = "12-12-2020";
        Assert.assertThrows(IllegalArgumentException.class, () -> new BatchBuilder().withExpiry(invalidExpiry));
    }

    @Test
    public void constructor_invalidQuantity_throwsIllegalArgumentException() {
        String invalidQuantity = "50.2";
        Assert.assertThrows(IllegalArgumentException.class, () -> new BatchBuilder().withQuantity(invalidQuantity));
    }

    @Test
    public void equals() {
        Batch defaultBatch = new BatchBuilder().build();

        // same values -> returns true
        assertTrue(defaultBatch.equals(new BatchBuilder().build()));

        // same object -> returns true
        assertTrue(defaultBatch.equals(defaultBatch));

        // null -> returns false
        assertFalse(defaultBatch.equals(null));

        // different type -> returns false
        assertFalse(defaultBatch.equals(5));

        // different name -> returns false
        Batch editedBatch = new BatchBuilder().withBatchNumber(VALID_BATCHNUMBER_AMOXICILLIN).build();
        assertFalse(defaultBatch.equals(editedBatch));

        // different quantity -> returns false
        editedBatch = new BatchBuilder().withQuantity(VALID_QUANTITY_AMOXICILLIN).build();
        assertFalse(defaultBatch.equals(editedBatch));

        // different expiry date-> returns false
        editedBatch = new BatchBuilder().withExpiry(VALID_EXPIRY_AMOXICILLIN).build();
        assertFalse(defaultBatch.equals(editedBatch));
    }
}
