/* @@author Carrein */
package seedu.address;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Config.SAMPLE_FOLDER;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.ResourceWalker;
import seedu.address.model.Album;
import seedu.address.testutil.Assert;

public class ResourceWalkerTest {
    private final ResourceWalker resourceWalker = new ResourceWalker();
    private final Album album = Album.getInstance();

    @Before
    public void init() {
        album.clearAlbum();
    }

    @Test
    public void invalid_walk() {
        Assert.assertThrows(NullPointerException.class, () -> resourceWalker.walk(null)); //null
        assertTrue(album.getImageList().size() == 0);
    }

    @Test
    public void valid_walk() {
        try {
            resourceWalker.walk(SAMPLE_FOLDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(album.getImageList().size() > 0);
    }
}
