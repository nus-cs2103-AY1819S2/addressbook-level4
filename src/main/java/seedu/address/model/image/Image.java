/* @@author Carrein */
package seedu.address.model.image;

import static seedu.address.commons.core.Config.ASSETS_FILEPATH;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

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
    private Name name;
    private Height height;
    private Width width;
    private BufferedImage buffer;
    private String url;
    private String fileType;

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
            this.url = url;
            this.name = new Name(file.getName());
            this.width = new Width(String.valueOf(buffer.getWidth()));
            this.height = new Height(String.valueOf(buffer.getHeight()));

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public Image(Name name, Height height, Width width) {
        this.name = name;
        this.height = height;
        this.width = width;
    }

    public Height getHeight() {
        return height;
    }

    public Width getWidth() {
        return width;
    }

    public Name getName() {
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
     * Returns true if both images have the same name.
     * This defines a weaker notion of equality between two images.
     */
    public boolean isSameImage(Image otherImage) {
        if (otherImage == this) {
            return true;
        }

        return otherImage != null
                && otherImage.getName().equals(getName());
    }

    /**
     * Returns true if both images have the same identity and data fields.
     * This defines a stronger notion of equality between two images.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Image)) {
            return false;
        }

        Image otherImage = (Image) other;
        return otherImage.getName().equals(getName())
                && otherImage.getWidth().equals(getWidth())
                && otherImage.getHeight().equals(getHeight());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, width, height);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Name: ")
                .append(getName())
                .append(" Height: ")
                .append(getHeight())
                .append(" Width: ")
                .append(getWidth());
        return builder.toString();
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
