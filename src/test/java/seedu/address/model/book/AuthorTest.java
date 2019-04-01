package seedu.address.model.book;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class AuthorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Author(null));
    }

    @Test
    public void constructor_invalidAuthor_throwsIllegalArgumentException() {
        String invalidAuthor = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Author(invalidAuthor));
    }

    @Test
    public void isValidAuthor() {
        // null author
        Assert.assertThrows(NullPointerException.class, () -> Author.isValidAuthor(null));

        String tooLongName = "This string is too long that it will not be accepted by author as "
                + "no one has such a long name";
        // invalid author
        assertFalse(Author.isValidAuthor("")); // empty string
        assertFalse(Author.isValidAuthor(" ")); // spaces only
        assertFalse(Author.isValidAuthor("^")); // only non-alphanumeric characters
        assertFalse(Author.isValidAuthor("peter*")); // contains non-alphanumeric characters
        assertFalse(Author.isValidAuthor(tooLongName)); // name too long to be considered as legal

        // valid author
        assertTrue(Author.isValidAuthor("peter jack")); // alphabets only
        assertTrue(Author.isValidAuthor("12345")); // numbers only
        assertTrue(Author.isValidAuthor("peter the 2nd")); // alphanumeric characters
        assertTrue(Author.isValidAuthor("Capital Tan")); // with capital letters
        assertTrue(Author.isValidAuthor("David Roger Jackson Ray Jr 2nd")); // long names
    }
}
