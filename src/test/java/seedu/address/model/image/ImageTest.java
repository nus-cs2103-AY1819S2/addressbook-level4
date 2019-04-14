/* @@author Carrein */
package seedu.address.model.image;

import java.io.File;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class ImageTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Image((String) null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Image("src/main/resources/imageTest/valid/valid/validPNGTest"));
    }

    @Test
    public void constructor_invalidFileArgument() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Image(new File("src/main/resources/imageTest/valid/valid/validPNGTest")));
    }
}
