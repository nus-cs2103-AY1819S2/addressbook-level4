package seedu.address.model.review;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class EntryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Entry(null));
    }

    @Test
    public void constructor_invalidEntry_throwsIllegalArgumentException() {
        String invalidEntry = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Entry(invalidEntry));
    }

    @Test
    public void isValidEntry() {
        // null Entry
        Assert.assertThrows(NullPointerException.class, () -> Entry.isValidEntry(null));

        // invalid entries
        assertFalse(Entry.isValidEntry("")); // empty string
        assertFalse(Entry.isValidEntry(" ")); // spaces only

        // valid entries
        assertTrue(Entry.isValidEntry("-")); // one character
        assertTrue(Entry.isValidEntry("Nice")); // short entry
        assertTrue(Entry.isValidEntry(
                "I loved the food! Service was amazing too! Would 100% come back again.")); // long entry
    }
}
