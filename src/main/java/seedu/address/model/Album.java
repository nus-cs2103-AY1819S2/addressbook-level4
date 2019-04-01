/* @@author Carrein */
package seedu.address.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Given a URL checks if the file is of an image type.
     *
     * @param url Path to a file or directory.
     * @return True if path is valid, false otherwise.
     */
    public boolean validFormat(String url) throws IOException {
        String mime = Files.probeContentType(Paths.get(url));
        return (mime != null && mime.split("/")[0].equals("image")) ? true : false;
    }
}
