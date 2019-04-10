/* @@author Carrein */
package seedu.address.model;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static seedu.address.commons.core.Config.ASSETS_FOLDER_TEMP_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.ImportCommandParser;
import seedu.address.model.image.Image;
import seedu.address.testutil.Assert;

public class AlbumTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public final Album album = Album.getInstance();

    @Before
    public void init() {
        album.clearAlbum();
    }

    @Test
    public void constructor() {
        assertEquals(album.getImageList(), Collections.emptyList());
    }

    @Test
    public void invalid_saveToAssets() {
        Assert.assertThrows(NullPointerException.class, () -> album.saveToAssets(null, null)); //null
    }

    @Test
    public void valid_getFileName() {
        assertParseSuccess(new ImportCommandParser(), "src/main/resources/imageTest/valid/iu.jpg",
                new ImportCommand(false));
        String[] array = {"iu.jpg"};
        assertArrayEquals(album.getFileNames(), array);
    }

    @Test
    public void valid_retrieveImage() {
        Image image = new Image("src/main/resources/imageTest/valid/iu.jpg");
        assertParseSuccess(new ImportCommandParser(), "src/main/resources/imageTest/valid/iu.jpg",
                new ImportCommand(false));
        assertEquals(album.retrieveImage("iu.jpg").toString(), image.toString());
    }

    @Test
    public void invalid_retrieveImage() {
        Assert.assertThrows(IllegalArgumentException.class, () -> album.retrieveImage(null)); //null
        Assert.assertThrows(IllegalArgumentException.class, () -> album.retrieveImage("")); //empty
    }

    @Test
    public void invalid_checkFileExist() {
        assertFalse(album.checkFileExist(null)); //null
        assertFalse(album.checkFileExist("")); //empty
        assertFalse(album.checkFileExist(" ")); //space
        assertFalse(album.checkFileExist("dalladalla")); //rubbish path
        assertFalse(album.checkFileExist("iu.jpg")); //valid path but does not exist
    }

    @Test
    public void valid_checkFileExist() {
        assertParseSuccess(new ImportCommandParser(), "src/main/resources/imageTest/valid/iu.jpg",
                new ImportCommand(false));
        assertTrue(album.checkFileExist("iu.jpg")); //valid path and file
    }

    @Test
    public void valid_clearAlbum() {
        assertParseSuccess(new ImportCommandParser(), "src/main/resources/imageTest/valid/iu.jpg",
                new ImportCommand(false));
        album.clearAlbum();
        List<Image> emptyList = new ArrayList<>();
        assertTrue(album.getImageList().size() == 0);
        assertTrue(album.getImageList().equals(emptyList));
    }

    @Test
    public void invalid_clearAlbum() {
        assertParseSuccess(new ImportCommandParser(), "src/main/resources/imageTest/valid/iu.jpg",
                new ImportCommand(false));
        album.clearAlbum();
        assertFalse(album.getImageList().size() > 0);
    }

    @Test
    public void valid_addToImageList() {
        album.addToImageList("src/main/resources/imageTest/valid/iu.jpg");
        assertTrue(album.getImageList().size() == 1);
    }

    @Test
    public void valid_populateAlbum() {
        album.addToImageList("src/main/resources/imageTest/valid/iu.jpg");
        assertTrue(album.getImageList().size() > 0);
    }

    @Test
    public void valid_generateAssets() {
        String tDir = System.getProperty("user.dir") + File.separator + ASSETS_FOLDER_TEMP_NAME + File.separator;
        assertEquals(album.generateAssets(), tDir);
        assertTrue(new File(tDir).exists());
    }

    @Test
    public void valid_imageExist() {
        album.addToImageList("src/main/resources/imageTest/valid/iu.jpg");
        assertTrue(album.getImageList().size() == 1);
    }

}
