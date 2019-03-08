package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class BookNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new BookName(null));
    }

    @Test
    public void constructor_invalidBookName_throwsIllegalArgumentException() {
        String invalidBookName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new BookName(invalidBookName));
    }

    @Test
    public void isValidBookName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> BookName.isValidBookName(null));

        // invalid name
        assertFalse(BookName.isValidBookName("")); // empty string
        assertFalse(BookName.isValidBookName(" ")); // spaces only
        assertFalse(BookName.isValidBookName("^")); // only non-alphanumeric characters
        assertFalse(BookName.isValidBookName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(BookName.isValidBookName("peter jack")); // alphabets only
        assertTrue(BookName.isValidBookName("12345")); // numbers only
        assertTrue(BookName.isValidBookName("peter the 2nd")); // alphanumeric characters
        assertTrue(BookName.isValidBookName("Capital Tan")); // with capital letters
        assertTrue(BookName.isValidBookName("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
