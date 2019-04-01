/* @@author Carrein */
package seedu.address.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import seedu.address.Notifier;
import seedu.address.model.image.Image;

/**
 * Represents an Album of Images in an ArrayList.
 * Album manages the propertyChangeListener for updates to the UI.
 * Uses singleton pattern to ensure only a single instance of Album is available.
 */
public class Album {
    // Represents the Storage path of assets folder for all raw images.
    private static final String ASSETS_FILEPATH = "src/main/resources/assets/";
    // Represents a singleton copy of the Album.
    private static Album instance = null;
    // Represents an ArrayList of image available in assets folder.
    private List<Image> imageList = new ArrayList<>();

    /**
     * Constructor for Album.
     * Checks if asset folder exists, creates it if it does not and populates the Album.
     */
    public Album() {
        assetExist();
        populateAlbum();
    }

    /**
     * Gets the current instance of Album.
     *
     * @return Returns the singleton Album instance.
     */
    public static Album getInstance() {
        if (instance == null) {
            instance = new Album();
        }
        return instance;
    }

    /**
     * Gets the list of images in the Album.
     *
     * @return A list of Image objects.
     */
    public List<Image> getImageList() {
        return imageList;
    }

    /**
     * Checks if an asset folder exists on Album construction.
     * Creates a new asset folder if one does not exist.
     */
    public void assetExist() {
        File assetFolder = new File(ASSETS_FILEPATH);
        if (!assetFolder.exists()) {
            assetFolder.mkdir();
        }
    }

    /**
     * For each image in assets folder, populate the Album with an Image object.
     */
    public void populateAlbum() {
        clearAlbum();
        File folder = new File(ASSETS_FILEPATH);
        for (File file : folder.listFiles()) {
            imageList.add(new Image(file.getAbsolutePath()));
        }
    }

    /**
     * Resets the Album to empty.
     */
    public void clearAlbum() {
        imageList.clear();
    }

    public void refreshAlbum() {
        Notifier.firePropertyChangeListener("refreshAlbum", null, null);
    }

    public void switchTab() {
        Notifier.firePropertyChangeListener("switchTab", null, null);
    }

    /* @@author*/
    /* @@author itszp*/

    /**
     * Check if file exists in assets folder.
     * Returns true if file name exists
     *
     * @param args string of file name.
     */
    public boolean checkFileExist(String args) {
        File file = new File(ASSETS_FILEPATH + args);
        return (file.isFile());
    }

    /**
     * Uses assetsFilePath to retrieve file as specified by args.
     * Returns an Image object.
     *
     * @param args string of file name.
     */
    public Image retrieveImage(String args) {
        return new Image(ASSETS_FILEPATH + args);
    }

    /**
     * Retrieves a list of all filenames in assets folder. Returns the list as String[].
     */
    public String[] getFileNames() {
        File file = new File(ASSETS_FILEPATH);
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
}
