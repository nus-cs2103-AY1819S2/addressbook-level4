package seedu.address.model.flashcard;
import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.imageio.ImageIO;

/**
 * Represents either empty (no image) or a valid path to an existing image.
 */
public class ImagePath {
    private Optional<String> imagePath;

    public ImagePath() {
        this.imagePath = Optional.empty();
    }

    public ImagePath(Optional<String> imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * @return whether or not an image path exists
     */
    public boolean hasImagePath() {
        return imagePath.isPresent();
    }

    /**
     * @return true iff this image path is either empty or has a valid image
     */
    public boolean imageExistsAtPath() {
        if (this.hasImagePath()) {
            File image = new File(imagePath.get());
            try {
                ImageIO.read(image);
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return the image path if it exists
     * @throws NoSuchElementException if no image path exists
     */
    public String getImagePath() throws NoSuchElementException {
        return imagePath.get();
    }

    /**
     * Returns true if both flashcards have the same identity and data fields. This defines a stronger notion of
     * equality between two flashcards.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImagePath)) {
            return false;
        }
        ImagePath imagePath = (ImagePath) o;
        if (!this.hasImagePath() && !imagePath.hasImagePath()) {
            return true;
        } else if (!this.hasImagePath() && imagePath.hasImagePath()) {
            return false;
        } else if (this.hasImagePath() && !imagePath.hasImagePath()) {
            return false;
        } else {
            return this.getImagePath().equals(imagePath.getImagePath());
        }
    }
}
