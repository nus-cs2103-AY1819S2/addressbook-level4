package seedu.address.model.nextofkin;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's NextOfKinRelation if any in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNextOfKinRelation(String)}
 */
public class NextOfKinRelation {

    public static final String MESSAGE_CONSTRAINTS =
        "NextOfKinRelation should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";
    public final String relationship;

    /**
     * Constructs a {@code NextOfKinRelation}.
     *
     * @param relationship A valid relationship.
     */
    public NextOfKinRelation(String relationship) {
        requireNonNull(relationship);
        checkArgument(isValidNextOfKinRelation(relationship), MESSAGE_CONSTRAINTS);
        this.relationship = relationship;
    }

    /**
     * Returns true if a given string is a valid next of kin relation.
     */
    public static boolean isValidNextOfKinRelation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getRelationship() {
        return relationship;
    }

    @Override
    public String toString() {
        return relationship;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof NextOfKinRelation // instanceof handles nulls
            && relationship.equals(((NextOfKinRelation) other).relationship)); // state check
    }

    @Override
    public int hashCode() {
        return relationship.hashCode();
    }
}
