/* @@author Carrein */
package seedu.address.model;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import seedu.address.model.image.Image;

/**
 * Represents an album of images. Uses singleton pattern to ensure only a
 * single instance of Album is available.
 */
public class Album {
    private static Album instance = null;
    private final String assetsFilePath = "src/main/resources/assets/";

    private List<Image> imageList;

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

    /* @@author itszp*/
    /**
     * Retrieves a list of all filenames in assets folder. Returns the list as String[].
     */
    public String[] getFileNames() {
        File file = new File(assetsFilePath);
        return file.list();
    }

    /**
     * Saves tempImage to assetsFolder as {@code name} or original name if not specified.
     */
    public void saveToAssets(Image image, String name) {
        try {
            File outputFile = new File(name);
            File saveDirectory = new File(ASSETS_FILEPATH);
            ImageIO.write(image.getBufferedImage(), image.getFileType(), outputFile);
            FileUtils.copyFileToDirectory(outputFile, saveDirectory, false);
            outputFile.delete();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
    /* @@author*/

    public void clearAlbum() {
        imageList.clear();
    }
}
