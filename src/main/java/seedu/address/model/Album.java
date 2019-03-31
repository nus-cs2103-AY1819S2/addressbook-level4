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
