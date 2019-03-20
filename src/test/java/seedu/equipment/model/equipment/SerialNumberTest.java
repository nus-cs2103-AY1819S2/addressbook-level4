package seedu.equipment.model.equipment;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.equipment.testutil.Assert;
import seedu.equipment.model.equipment.SerialNumber;

public class SerialNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new SerialNumber(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidSerialNumber = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new SerialNumber(invalidSerialNumber));
    }

    @Test
    public void isValidSerialNumber() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> SerialNumber.isValidSerialNumber(null));

        // invalid name
        assertFalse(SerialNumber.isValidSerialNumber("")); // empty string
        assertFalse(SerialNumber.isValidSerialNumber(" ")); // spaces only
        assertFalse(SerialNumber.isValidSerialNumber("^")); // only non-alphanumeric characters
        assertFalse(SerialNumber.isValidSerialNumber("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(SerialNumber.isValidSerialNumber("AAUEURYA")); // alphabets only
        assertTrue(SerialNumber.isValidSerialNumber("12345")); // numbers only
        assertTrue(SerialNumber.isValidSerialNumber("A008866X")); // alphabets and numbers
        assertTrue(SerialNumber.isValidSerialNumber("A008866x")); // with capital letters
    }
}
