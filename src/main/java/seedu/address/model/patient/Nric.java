package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

/**
 * Represents a Person's Nric in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {

    public static final String MESSAGE_CONSTRAINTS =
        "Nric is compulsory, denoted by " + PREFIX_NRIC + " and should be in standard format.";
    private static final String VALIDATION_REGEX = "^[STFG]\\d{7}[A-Z]$";
    private final String value;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid Nric number.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS + " , provided: " + nric);
        value = nric;
    }

    /**
     * Returns true if a given string is a valid Nric number, or intentionally left empty (-).
     * @param test the string to be tested.
     */
    public static boolean isValidNric(String test) {
        requireNonNull(test);
        return test.matches(VALIDATION_REGEX);
    }

    public String getNric() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Nric // instanceof handles nulls
            && value.equals(((Nric) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
