package seedu.address.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class FileNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new FileName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidFileName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new FileName(invalidFileName));
    }

    @Test
    public void isValidFileName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> FileName.isValidFileName(null));

        // invalid file name (Windows reserved names)
        // The Windows reserved names are as follows:
        // CON, PRN, AUX, NUL, COM0, COM1, COM2, COM3, COM4, COM5, COM6, COM7, COM8, COM9, LPT0, LPT1, LPT2, LPT3, LPT4,
        // LPT5, LPT6, LPT7, LPT8, and LPT9
        assertFalse(FileName.isValidFileName("CON"));
        assertFalse(FileName.isValidFileName("PRN"));
        assertFalse(FileName.isValidFileName("AUX"));
        assertFalse(FileName.isValidFileName("NUL"));
        assertFalse(FileName.isValidFileName("COM0"));
        assertFalse(FileName.isValidFileName("COM1"));
        assertFalse(FileName.isValidFileName("COM2"));
        assertFalse(FileName.isValidFileName("COM3"));
        assertFalse(FileName.isValidFileName("COM4"));
        assertFalse(FileName.isValidFileName("COM5"));
        assertFalse(FileName.isValidFileName("COM6"));
        assertFalse(FileName.isValidFileName("COM7"));
        assertFalse(FileName.isValidFileName("COM8"));
        assertFalse(FileName.isValidFileName("COM9"));
        assertFalse(FileName.isValidFileName("LPT0"));
        assertFalse(FileName.isValidFileName("LPT1"));
        assertFalse(FileName.isValidFileName("LPT2"));
        assertFalse(FileName.isValidFileName("LPT3"));
        assertFalse(FileName.isValidFileName("LPT4"));
        assertFalse(FileName.isValidFileName("LPT5"));
        assertFalse(FileName.isValidFileName("LPT6"));
        assertFalse(FileName.isValidFileName("LPT7"));
        assertFalse(FileName.isValidFileName("LPT8"));
        assertFalse(FileName.isValidFileName("LPT9"));
        // Reserved names in lower case
        assertFalse(FileName.isValidFileName("con"));
        assertFalse(FileName.isValidFileName("prn"));
        assertFalse(FileName.isValidFileName("aux"));
        assertFalse(FileName.isValidFileName("nul"));
        assertFalse(FileName.isValidFileName("com0"));
        assertFalse(FileName.isValidFileName("com1"));
        assertFalse(FileName.isValidFileName("com2"));
        assertFalse(FileName.isValidFileName("com3"));
        assertFalse(FileName.isValidFileName("com4"));
        assertFalse(FileName.isValidFileName("com5"));
        assertFalse(FileName.isValidFileName("com6"));
        assertFalse(FileName.isValidFileName("com7"));
        assertFalse(FileName.isValidFileName("com8"));
        assertFalse(FileName.isValidFileName("com9"));
        assertFalse(FileName.isValidFileName("lpt0"));
        assertFalse(FileName.isValidFileName("lpt1"));
        assertFalse(FileName.isValidFileName("lpt2"));
        assertFalse(FileName.isValidFileName("lpt3"));
        assertFalse(FileName.isValidFileName("lpt4"));
        assertFalse(FileName.isValidFileName("lpt5"));
        assertFalse(FileName.isValidFileName("lpt6"));
        assertFalse(FileName.isValidFileName("lpt7"));
        assertFalse(FileName.isValidFileName("lpt8"));
        assertFalse(FileName.isValidFileName("lpt9"));
        // Reserve names with a mix of lower and upper case
        assertFalse(FileName.isValidFileName("CoM1"));

        // invalid file name
        assertFalse(FileName.isValidFileName("")); // empty string
        assertFalse(FileName.isValidFileName(" ")); // spaces only
        assertFalse(FileName.isValidFileName("^")); // only not supported non-alphanumeric characters
        assertFalse(FileName.isValidFileName("example*")); // contains not supported non-alphanumeric characters
        assertFalse(FileName.isValidFileName("example record")); // alphabets with a space only
        assertFalse(FileName.isValidFileName("exampleRecord.csv")); // alphabets with file format
        assertFalse(FileName.isValidFileName(".csv")); // file format
        assertFalse(FileName.isValidFileName("_")); // underscore only
        assertFalse(FileName.isValidFileName("-")); // hyphen only

        // valid file name
        assertTrue(FileName.isValidFileName("12345")); // numbers only
        assertTrue(FileName.isValidFileName("examplerecord")); // alphanumeric characters
        assertTrue(FileName.isValidFileName("exampleRecord2")); // with numbers
        assertTrue(FileName.isValidFileName("exampleRecord")); // with capital letters
        assertTrue(FileName.isValidFileName("example_Record")); // with underscore
        assertTrue(FileName.isValidFileName("example-record")); // with hyphen
        assertTrue(FileName.isValidFileName("COM10"));
        assertTrue(FileName.isValidFileName("LPT10"));
    }

    @Test
    public void equals_inputObjectEquals_trueReturned() {
        FileName expected = new FileName("example");
        FileName actual = new FileName("example");

        assertTrue(actual.equals(expected));
    }

    @Test
    public void equals_inputObjectNotEquals_falseReturned() {
        FileName expected = new FileName("example2");
        FileName actual = new FileName("example");

        assertFalse(actual.equals(expected));
    }
}
