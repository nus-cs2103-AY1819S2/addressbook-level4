/* @@author Carrein */
package seedu.address.model;

import static seedu.address.commons.core.Config.ASSETS_FOLDER_TEMP_NAME;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import seedu.address.model.image.Image;
import seedu.address.ui.Notifier;

/**
 * Represents an Album of Images in an ArrayList.
 * Album manages the propertyChangeListener for updates to the UI.
 * Uses singleton pattern to ensure only a single instance of Album is available.
 */
public class Album {
    // Represents a singleton copy of the Album.
    private static Album instance;

    // Represents the Storage path of assets folder for all raw images.
    private final String assetsFilepath;

    // Represents an ArrayList of image available in assets folder.
    private List<Image> imageList = new ArrayList<>();

    /**
     * Constructor for Album.
     * Checks if asset folder exists, creates it if it does not and populates the Album.
     */
    private Album() {
        assetsFilepath = generateAssets();
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
     * Gets the filepath of assets folder.
     *
     * @return String of filepath location.
     */
    public String getAssetsFilepath() {
        return assetsFilepath;
    }

    /**
     * Checks if image exists in assets directory.
     *
     * @param imageName Image name to check for.
     * @return True if image exist, false otherwise.
     */
    public boolean imageExist(String imageName) {
        for (Image i : imageList) {
            if (i.getName().fullName.equals(imageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a temp assets folder in the system to store imported images.
     * Temp assets folder is deleted on termination of the program by means of shutdown hook.
     *
     * @return Absolute path to generated temp folder.
     */
    public String generateAssets() {
        String tempPath = null;
        try {
            String tDir = System.getProperty("user.dir") + File.separator + ASSETS_FOLDER_TEMP_NAME;
            File assetsFolder = new File(tDir);
            tempPath = assetsFolder.getAbsolutePath() + File.separator;
            if (!assetsFolder.exists()) {
                assetsFolder.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return tempPath;
        }
    }


    /**
     * For each image in assets folder, populate the Album with an Image object.
     */
    public void populateAlbum() {
        imageList.clear();
        try {
            File folder = new File(assetsFilepath);
            for (File file : folder.listFiles()) {
                addToImageList(file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToImageList(String args) {
        imageList.add(new Image(args));
    }

    /**
     * Resets the Album to empty.
     */
    public void clearAlbum() {
        imageList.clear();
        try {
            FileUtils.cleanDirectory(new File(assetsFilepath));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * @param args is the string of the file name.
     */
    public boolean checkFileExist(String args) {
        File file = new File(assetsFilepath + args);
        return (file.isFile());
    }

    /**
     * Uses assetsFilePath to retrieve file as specified by args.
     * Returns an Image object.
     *
     * @param args is the string of the file name.
     */
    public Image retrieveImage(String args) {
        return new Image(assetsFilepath + args);
    }

    /**
     * Retrieves all the filenames in assets folder. Returns them in a string array.
     */
    public String[] getFileNames() {
        File file = new File(assetsFilepath);
        return file.list();
    }

    /**
     * Saves the image to assetsFolder.
     *
     * @param image is the new image.
     * @param name is the name of the new image file.
     */
    public void saveToAssets(Image image, String name) {
        try {
            File outputFile = new File(name);
            File saveDirectory = new File(assetsFilepath);
            ImageIO.write(image.getBufferedImage(), image.getFileType(), outputFile);
            FileUtils.copyFileToDirectory(outputFile, saveDirectory, false);
            outputFile.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /* @@author*/

    /**
     *
     * @param imageName
     * @return
     */
    public Image getImageFromList(String imageName) {
        Image tempImage = null;
        for (Image i : imageList) {
            if (imageName.equals(i.getName().toString())) {
                tempImage = i;
            }
        }
        return tempImage;
    }
}
