package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.person.Person;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class CopyTag extends Tag {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be like $Copy1";
    public static final String VALIDATION_REGEX = "(\\$)(Copy)([0-9]+)";

    public final String tagName;
    private final Person originalPerson;

    /**
     * Constructs a {@code CopyTag}.
     *
     * @param personToCopy A pointer to the original person
     * @param tagName A valid tag name.
     */
    public CopyTag(Person personToCopy, String tagName) {
        super("Copy");
        requireNonNull(personToCopy);
        originalPerson = personToCopy;
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public Person getOriginalPerson() {
        return originalPerson;
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
        return '[' + tagName + ']';
    }

}
