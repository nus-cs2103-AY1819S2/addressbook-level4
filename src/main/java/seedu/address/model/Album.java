/* @@author Carrein */

package seedu.address.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import seedu.address.model.image.Image;

/**
 * Represents an album of images.
 * Uses singleton pattern to
 * ensure only a
 * single instance of Album is available.
 */
public class Album {
    private static Album instance = null;
    private final String assetsFilePath = "src/main/resources/assets/";

    private List<Image> imageList;
    private File folder;

    public Album() {
        imageList = new ArrayList<>();
        populateAlbum();
    }

    public static Album getInstance() {
        if (instance == null) {
            instance = new Album();
        }
        return instance;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void addImage(Image image) {
        imageList.add(image);
    }

    /**
     * Populates album on method call with images in assets folder.
     */
    public void populateAlbum() {
        File folder = new File(assetsFilePath);
        for (File file : folder.listFiles()) {
            imageList.add(new Image(file.getAbsolutePath()));
        }
    }

    public void clearAlbum() {
        imageList.clear();
    }
}
