package seedu.address.model.image;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
    private List commandHistory;
    private int index;

    /**
     * Every field must be present and not null.
     */
    public Image(String url) {
        requireAllNonNull(url);
        try {
            File file = new File(url);
            buffer = ImageIO.read(file);
            this.name = file.getName();
            this.height = buffer.getHeight();
            this.width = buffer.getWidth();
            commandHistory = new List();
            index = 0;

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public Image(String name, int height, int width) {
        this.name = name;
        this.height = height;
        this.width = width;
    }

    public void addHistory(String c) {
        commandHistory.add(c);
        index++;
    }

    public int getIndex() { return index; }

    public int getWidth() { return width; }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public BufferedImage getBufferedImage() {
        return buffer;
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
