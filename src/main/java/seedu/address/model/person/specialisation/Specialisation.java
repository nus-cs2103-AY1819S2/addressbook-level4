package seedu.address.model.person.specialisation;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Specialisation of a doctor in docX.
 * Guarantees: immutable; name is valid as declared in {@link #isValidSpecialisation(String)}
 */
public class Specialisation {

    public static final String MESSAGE_CONSTRAINTS = "Specialisations should be alphanumeric and cannot be empty";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String specialisation;

    /**
     * Constructs a {@code Specialisation}.
     *
     * @param specialisation A valid specialisation.
     */
    public Specialisation(String specialisation) {
        requireNonNull(specialisation);
        checkArgument(isValidSpecialisation(specialisation), MESSAGE_CONSTRAINTS);
        this.specialisation = specialisation;
    }

    /**
     * Returns true if a given string is a valid specialisation.
     */
    public static boolean isValidSpecialisation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Specialisation // instanceof handles nulls
                && specialisation.equals(((Specialisation) other).specialisation)); // state check
    }

    @Override
    public int hashCode() {
        return specialisation.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + specialisation + ']';
    }

}
