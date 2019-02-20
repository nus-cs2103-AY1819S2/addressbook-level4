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
    }
}
