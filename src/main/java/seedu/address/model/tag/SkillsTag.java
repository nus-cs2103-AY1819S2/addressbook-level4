package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a SkillsTag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class SkillsTag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "[\\p{ASCII}][\\p{ASCII} ]*";

    public final String tagName;
    public final String tagColor;
    public final String tagType;

    /**
     * Constructs a {@code SkillsTag}.
     *
     * @param tagName A valid tag name.
     * @param type
     */
    public SkillsTag(String tagName, String type) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagType = type;
        if (type.equals("skill")) {
            //skill tag
            this.tagColor = "pink";
            this.tagName = "s:" + tagName;

        } else if (type.equals("pos")) {
            //position tag
            this.tagColor = "yellow";
            this.tagName = "p:" + tagName;

        } else {
            //endorsement tag
            this.tagColor = "teal";
            this.tagName = "e:" + tagName;
        }
    }

    public SkillsTag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
        this.tagColor = null;
        this.tagType = null;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SkillsTag // instanceof handles nulls
                && tagName.equals(((SkillsTag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
