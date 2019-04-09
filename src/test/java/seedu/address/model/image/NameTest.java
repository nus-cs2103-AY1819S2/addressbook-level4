package seedu.address.model.image;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class NameTest {

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
}
