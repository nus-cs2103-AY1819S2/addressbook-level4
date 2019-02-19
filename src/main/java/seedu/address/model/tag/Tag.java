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
    public static final String VALIDATION_REGEX = "\\p{Alnum}+(/\\p{Alnum}+)?";
    private static final String[] TAG_COLOR_STYLES = { "red", "yellow", "blue", "green", "grey", "magenta", "pink" };


    public final String tagName;
    public final String tagColor;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagNameAndColor A valid tag name.
     */
    public Tag(String tagNameAndColor) {
        requireNonNull(tagNameAndColor);
        checkArgument(isValidTagName(tagNameAndColor), MESSAGE_CONSTRAINTS);
        String[] attributes = tagNameAndColor.split("/");
        this.tagName = attributes[0];
        if (attributes.length == 1) {
            tagColor = TAG_COLOR_STYLES[(Math.abs(tagName.hashCode()) % TAG_COLOR_STYLES.length)];
        } else {
            checkArgument(isValidTagColor(attributes[1]), COLOR_CONSTRAINTS);
            tagColor = attributes[1];
        }
    }

    /**
     * Returns true if a given string is a valid tag name and color.
     */
    public static boolean isValidTagName(String test) {
        boolean matchesPattern = test.matches(VALIDATION_REGEX);
        String[] attributes = test.split("/");
        if (attributes.length == 1) {
            return matchesPattern;
        }
        return matchesPattern && isValidTagColor(attributes[1]);
    }

    /**
     * Returns true if the given tag color is a valid color.
     */
    private static boolean isValidTagColor(String test) {
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
