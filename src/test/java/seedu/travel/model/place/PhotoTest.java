package seedu.travel.model.place;

import org.junit.Test;
import seedu.travel.testutil.Assert;

import static org.junit.Assert.assertFalse;

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
        assertFalse(Photo.isValidPhotoFilepath("  ")); // spaces only
        assertFalse(Photo.isValidPhotoFilepath("C:\\Users\\Shaun\\Documents\\SourceTree Repos" +
                "\\addressbook-level4\\docs\\images\\TravelBuddy-icon.png")); // filepath with whitespace
    }
}
