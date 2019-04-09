package seedu.travel.model.place;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.travel.testutil.Assert;

public class PhotoTest {

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
}
