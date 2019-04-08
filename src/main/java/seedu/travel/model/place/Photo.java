package seedu.travel.model.place;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

import static java.util.Objects.requireNonNull;
import static seedu.travel.commons.util.AppUtil.checkArgument;

/**
 * Represents a Place's country code in TravelBuddy.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhotoFilepath(String)} (String)}
 */
public class Photo {

    public static final String MESSAGE_CONSTRAINTS = "File path must be an absolute file path, and point to a photo." +
            "Ensure that folder names and filenames are without spaces, eg. " +
            " C:\\Users\\<your-username>\\Pictures\\your-travel-photo.jpg";
    private final String filepath;

    /**
     * Constructs a {@code photoFilepath}.
     *
     * @param photoFilepath A valid filepath for the photo.
     */
    public Photo(String photoFilepath) {
        requireNonNull(photoFilepath);
        checkArgument(isValidPhotoFilepath(photoFilepath), MESSAGE_CONSTRAINTS);
        this.filepath = photoFilepath;
    }

    /**
     * @Return the valid filepath of this Photo
     */
    public String getFilePath() {
        return this.filepath;
    }

    /**
     * Returns true if a given string is a valid filepath, checks for filepath format and spaces
     */
    public static boolean isValidPhotoFilepath(String test) {
        if (!(test instanceof String)) {
            throw new NullPointerException();
        }

        try {
            File testPhoto = new File(test);
            Image image = ImageIO.read(testPhoto);
            FileInputStream testStream = new FileInputStream(testPhoto);

            if (image == null) {
                System.out.println("The file "+testPhoto+" could not be opened, it is not an image");
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return filepath;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Photo // instanceof handles nulls
            && filepath.equals(((Photo) other).filepath)); // state check
    }

    @Override
    public int hashCode() {
        return filepath.hashCode();
    }

}
