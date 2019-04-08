package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's ID.
 * Guarantees: immutable; is valid as declared in {@link #isValidPersonId(String)}
 */
public class PersonId {

    public static final String MESSAGE_CONSTRAINTS =
            "ID must a positive number.";
    public final int personId;

    /**
     * Constructs a {@code PersonId}.
     *
     * @param personId a valid person ID.
     */
    public PersonId(String personId) {
        requireNonNull(personId);
        checkArgument(isValidPersonId(personId), MESSAGE_CONSTRAINTS);
        this.personId = Integer.parseInt(personId);
    }

    public PersonId(int personId) {
        this.personId = personId;
    }

    /**
     * Returns true if a given string is a valid person ID.
     */
    public static boolean isValidPersonId(String test) {
        int positiveNumber;
        try {
            positiveNumber = Integer.parseInt(test);
        } catch (NumberFormatException e) {
            return false;
        }

        if (positiveNumber > 0) {
            return true;

        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonId // instanceof handles nulls
                && this.personId == ((PersonId) other).personId); // state check
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(personId);
    }


    @Override
    public String toString() {
        return Integer.toString(personId);
    }
}
