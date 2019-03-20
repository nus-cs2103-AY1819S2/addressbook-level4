package seedu.address.model.image;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

/**
 * Represents an Image in FomoFoto
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Image {

    // Data fields
    private String name;
    private int height;
    private int width;
    private BufferedImage buffer;
    private String url;
    private String fileType;
    private List<String> commandHistory;
    private int index;

    /**
     * Every field must be present and not null.
     */
    public Image(String url) {
        requireAllNonNull(url);
        try {
            File file = new File(url);
            buffer = ImageIO.read(file);
            try {
                ImageInputStream iis = ImageIO.createImageInputStream(file);

                Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);

                int readTime = 0;
                while (readTime < 1 && imageReaders.hasNext()) {
                    ImageReader reader = imageReaders.next();
                    fileType = reader.getFormatName().toLowerCase();
                    readTime++;
                    System.out.println(fileType);
                }
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            this.name = file.getName();
            this.height = buffer.getHeight();
            this.width = buffer.getWidth();
            this.url = url;
            this.commandHistory = new ArrayList<>();
            this.index = 1;

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public Image(String name, int height, int width) {
        this.name = name;
        this.height = height;
        this.width = width;
    }

    /**
     * method to track history of image
     * @param c command to be added to history
     */
    public void addHistory(String c) {
        commandHistory.add(c);
        index++;
    }
    //CAN USE JAVA UTIL LIST HERE???
    public List getHistory() {
        return commandHistory.subList(0, index);
    }

    public void setUndo() {
        index--;
    }

    public void setRedo() {
        index++;
    }

    public boolean canUndo() {
        return index > 0;
    }

    public boolean canRedo() {
        return index < commandHistory.size() - 1;
    }

    public int getIndex() {
        return index; }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getBufferedImage() {
        return buffer;
    }

    public String getUrl() {
        return url;
    }

    public String getFileType() {
        return fileType;
    }
    /**
     * Prints the metadata for any given image.
     */
    public void printMetadata() throws IOException, ImageProcessingException {
        Metadata metadata = ImageMetadataReader.readMetadata(new File(ASSETS_FILEPATH + this.name));
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }
    }
}
