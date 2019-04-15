package seedu.address.model.skill;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Skill in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSkillName(String)}
 */
public class Skill {

    public static final String MESSAGE_CONSTRAINTS = "Skill names should not be empty";

    public final String skillName;

    /**
     * Constructs a {@code Skill}.
     *
     * @param skillName A valid skill name.
     */
    public Skill(String skillName) {
        requireNonNull(skillName);
        checkArgument(isValidSkillName(skillName), MESSAGE_CONSTRAINTS);
        this.skillName = skillName;
    }

    /**
     * Returns a clone of this Skill object.
     */
    public Skill clone() {
        return new Skill(this.skillName);
    }

    /**
     * Returns true if a given string is a valid skill name.
     */
    public static boolean isValidSkillName(String test) {
        return !test.trim().isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Skill // instanceof handles nulls
            && skillName.equals(((Skill) other).skillName)); // state check
    }

    @Override
    public int hashCode() {
        return skillName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + skillName + ']';
    }

}
