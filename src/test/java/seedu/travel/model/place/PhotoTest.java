package seedu.travel.model.place;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import seedu.travel.testutil.Assert;

public class PhotoTest {

    public static final String EMPTY_PHOTO_PATH = "pBSgcMnA";

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Photo(null));
    }

    @Test
    public void constructor_invalidPhoto_throwsIllegalArgumentException() {
        String invalidPhoto = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Photo(invalidPhoto));
    }

    @Test
    public void isValidPhoto() {
        // null photo filepath
        Assert.assertThrows(NullPointerException.class, () -> Photo.isValidPhotoFilepath(null));

        // invalid filepath
        assertFalse(Photo.isValidPhotoFilepath("")); // empty string
        assertFalse(Photo.isValidPhotoFilepath(" ")); // spaces only
        assertFalse(Photo.isValidPhotoFilepath("*)$W#")); // not a filepath
    }

    @Test
    public void hashCodeTest() {
        Photo test1 = new Photo(EMPTY_PHOTO_PATH);
        Photo test2 = new Photo(EMPTY_PHOTO_PATH);
        test2.setFilepath(" ");

        assertEquals(test1.hashCode(), test1.hashCode());
        assertNotEquals(test1.hashCode(), test2.hashCode());
    }
}
