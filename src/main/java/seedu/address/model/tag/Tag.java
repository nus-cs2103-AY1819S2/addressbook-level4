package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String COLOR_CONSTRAINTS = "Color is not supported";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";
    private static final String[] TAG_COLOR_STYLES = { "red", "yellow", "blue", "green", "grey" };


    public final String tagName;
    public final String tagColor;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
        this.tagColor = TAG_COLOR_STYLES[(Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length)];
    }

    /**
     * Constructs a {@code Tag} from {@code tagName} and {@code tagColor}.
     *
     * @param tagName A valid tag name
     * @param tagColor A valid tagColor
     */
    public Tag(String tagName, String tagColor) {
        requireNonNull(tagName);
        checkArgument(isValidTagColor(tagColor), COLOR_CONSTRAINTS);
        this.tagName = tagName;
        this.tagColor = tagColor;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true is the given tag color is a valid color
     */
    public static boolean isValidTagColor(String test) {
        for (String color: TAG_COLOR_STYLES) {
            if (test.compareTo(color) == 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagName.equals(((Tag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + '/' + tagColor + ']';
    }

}
