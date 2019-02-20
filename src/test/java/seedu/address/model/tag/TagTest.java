package seedu.address.model.tag;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import seedu.address.testutil.Assert;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void isVIPTag() {
        // "VIP" and "vip" -> return true
        assertTrue(new Tag("VIP").isVIPTag());
        assertTrue(new Tag("vip").isVIPTag());

        // Other tag name -> returns false
        assertFalse(new Tag("VI").isVIPTag());
    }

}
