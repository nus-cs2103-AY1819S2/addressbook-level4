package seedu.address.model.flashcard;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Flashcard's face in the card collection.
 * Guarantees: immutable
 */
public class Face {

    public static final String MESSAGE_CONSTRAINTS = "Face can take any values, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String text;


    /**
     * Constructs a {@code Face}.
     *
     * @param text A valid text.
     */
    public Face(String text) {
        requireNonNull(text);
        checkArgument(isValidFace(text), MESSAGE_CONSTRAINTS);
        this.text = text;
    }

    public static boolean isValidFace(String trimmedFace) {
        return trimmedFace.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Face face = (Face) o;
        return text.equals(face.text);
    }
}
