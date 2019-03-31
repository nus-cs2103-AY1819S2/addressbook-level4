/* @@author Carrein */
package seedu.address.model;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        clearAlbum();
        File folder = new File(assetsFilePath);
        for (File file : folder.listFiles()) {
            try {
                if (validFormat(file.getAbsolutePath())) {
                    imageList.add(new Image(file.getAbsolutePath()));
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    /* @@author itszp*/
    /**
     * Check if file exists in assets folder.
     * Returns true if file name exists
     *
     * @param args string of file name.
     */
    public boolean checkFileExists(String args) {
        File file = new File(assetsFilePath + args);
        return (file.isFile());
    }

    /**
     * Uses assetsFilePath to retrieve file as specified by args.
     * Returns an Image object.
     *
     * @param args string of file name.
     */
    public Image retrieveImage(String args) {
        return new Image(assetsFilePath + args);
    }

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
