/* @@author Carrein */
package seedu.address.model.image;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NameTest {

    private final Name name = new Name("sample", "jpg", "sample.jpg");
    private final Name dup = new Name("sample", "jpg", "sample.jpg");

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null, null, null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName, invalidName, invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidExtension(null));

        // invalid name
        assertFalse(Name.isValidExtension("")); // empty string
        assertFalse(Name.isValidExtension(" ")); // spaces only
        assertFalse(Name.isValidExtension("sample.txt")); // invalid file format
        assertFalse(Name.isValidExtension("sample.")); // missing extensions
        assertFalse(Name.isValidExtension("sample")); // raw name

        // valid name
        assertTrue(Name.isValidExtension("sample.jpg")); // JPG format
        assertTrue(Name.isValidExtension("sample.tiff")); // TIFF format
        assertTrue(Name.isValidExtension("sample.bmp")); // BMP format
        assertTrue(Name.isValidExtension("sample.gif")); // GIF format
        assertTrue(Name.isValidExtension("sample.png")); // PNG format
    }

    @Test
    public void valid_getBaseName() {
        assertEquals(name.getBaseName(), "sample");
    }

    @Test
    public void valid_getExtName() {
        assertEquals(name.getExtName(), "jpg");
    }

    @Test
    public void valid_getFullName() {
        assertEquals(name.getFullName(), "sample.jpg");

    }

    @Test
    public void equality() {
        assertTrue(name.equals(dup));
    }
}
