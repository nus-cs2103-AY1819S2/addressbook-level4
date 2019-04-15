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

        String tooLongStr = "This string is too long that it will not be accepted by some attributes. "
                + "For example, the book name cannot be such long.";
        // invalid name
        assertFalse(BookName.isValidBookName("")); // empty string
        assertFalse(BookName.isValidBookName(" ")); // spaces only
        assertFalse(BookName.isValidBookName(tooLongStr)); // book name too long
        assertFalse(BookName.isValidBookName("A Book Name with /c")); // illegal character

        // valid name
        assertTrue(BookName.isValidBookName("peter jack")); // alphabets only
        assertTrue(BookName.isValidBookName("12345")); // numbers only
        assertTrue(BookName.isValidBookName("peter the 2nd")); // alphanumeric characters
        assertTrue(BookName.isValidBookName("Capital Tan")); // with capital letters
        assertTrue(BookName.isValidBookName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(BookName.isValidBookName("JoJo's Bizarre Adventure")); // contains non-alphnum character
    }
}
