package seedu.address.model.medicine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class BatchNumberTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new BatchNumber(null));
    }

    @Test
    public void constructor_invalidBatchNumber_throwsIllegalArgumentException() {
        String invalidBatchNumber = "!!!!";
        Assert.assertThrows(IllegalArgumentException.class, () -> new BatchNumber(invalidBatchNumber));
    }

    @Test
    public void isValidBatchNumber() {
        // null
        Assert.assertThrows(NullPointerException.class, () -> BatchNumber.isValidBatchNumber(null));

        // invalid batch numbers
        assertFalse(BatchNumber.isValidBatchNumber("")); // empty string
        assertFalse(BatchNumber.isValidBatchNumber(" ")); // spaces only
        assertFalse(BatchNumber.isValidBatchNumber("^")); // only non-alphanumeric characters
        assertFalse(BatchNumber.isValidBatchNumber("SX 1484*")); // contains illegal non-alphanumeric characters
        assertFalse(BatchNumber.isValidBatchNumber("111111111111111111111111111111111111111111111111111")); // too long

        // valid batch numbers
        assertTrue(BatchNumber.isValidBatchNumber("ABCDE")); // alphabets only
        assertTrue(BatchNumber.isValidBatchNumber("12345")); // numbers only
        assertTrue(BatchNumber.isValidBatchNumber("SX 1484 852")); // alphanumeric characters with spaces
        assertTrue(BatchNumber.isValidBatchNumber("NC 154-4815")); // with '-'
    }
}
