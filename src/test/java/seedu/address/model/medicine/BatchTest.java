package seedu.address.model.medicine;

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
}
