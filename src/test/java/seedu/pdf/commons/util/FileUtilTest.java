package seedu.pdf.commons.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.pdf.testutil.Assert;

public class FileUtilTest {

    @Test
    public void isValidPath() {
        // valid value
        assertTrue(FileUtil.isValidPath("valid/file/value"));

        // invalid value
        assertFalse(FileUtil.isValidPath("a\0"));

        // null value -> throws NullPointerException
        Assert.assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

}
