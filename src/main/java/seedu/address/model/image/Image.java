/* @@author Carrein */
package seedu.address.model.image;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

/**
 * Represents an Image in FomoFoto.
 */
public class Image {

    /**
     * Data fields.
     */
    private Name name;
    private Height height;
    private Width width;
    private Size size;
    private BufferedImage buffer;
    private String url;
    private String fileType;
    private Metadata metadata;

    /**
     * Every field must be present and not null.
     */
    public Image(String url) {
        requireAllNonNull(url);
        try {
            File file = new File(url);
//<<<<<<< HEAD
//            buffer = ImageIO.read(file);
//            this.size = new Size(String.valueOf(file.length()));
//
//            try {
//                ImageInputStream iis = ImageIO.createImageInputStream(file);
//
//                Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(iis);
//
//                int readTime = 0;
//                while (readTime < 1 && imageReaders.hasNext()) {
//                    ImageReader reader = imageReaders.next();
//                    fileType = reader.getFormatName().toLowerCase();
//                    readTime++;
//                }
//            } catch (IOException e) {
//                System.out.println(e.toString());
//            }
//=======
            this.buffer = ImageIO.read(file);
            this.fileType = FilenameUtils.getExtension(url);
            this.size = new Size(String.valueOf(file.length()));

            this.url = url;
            this.name = new Name(file.getName());
            this.width = new Width(String.valueOf(buffer.getWidth()));
            this.height = new Height(String.valueOf(buffer.getHeight()));
            this.metadata = ImageMetadataReader.readMetadata(file);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println(this.toString());
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

    public Size getSize() {
        return size;
    }

    public Metadata getMetadata() {
        return metadata;
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

    /**
     * A basic representation of the Image's fields.
     * EG:
     * Name: sample.png
     * Height: 1600
     * Width: 1600
     *
     * @return the fields of the image.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("====================")
                .append("\nName: ")
                .append(getName())
                .append("\nHeight: ")
                .append(getHeight())
                .append("\nWidth: ")
                .append(getWidth())
                .append("\nFormat: ")
                .append(getFileType())
                .append("\nURL: ")
                .append(getUrl())
                .append("\n====================");
        return builder.toString();
    }

    /**
     * Returns metadata for image in an ArrayList of Strings.
     *
     * @return list of meta data tags.
     */
    public List<String> getMetadataList() {
        List<String> tempList = new ArrayList<>();
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                tempList.add(tag.toString());
            }
        }
        return tempList;
    }
}
