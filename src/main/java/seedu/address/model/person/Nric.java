package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's Nric to be represented.
 * Guarantees Non-Null and is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {

    public static final String MESSAGE_CONSTRAINTS = "Nric should start with "
            + "an S or T, followed by 7 numeric characters and then a letter"
            + ". All letters are to be in capital casing";
    public static final String VALIDATION_REGEX = "[ST]\\d{7}[A-Z]"; // Nric
    public static final String DEFAULT_NRIC = "S1234567A";
    // should start with capital S/T, followed by 7 numeric characters and a
    // capital letter.

    private String nric;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid Nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        this.nric = nric;
    }

    /**
     * Returns true if the Nric entered matches is valid.
     * @param test Nric string to be tested
     * @return true if test matches the validation regex
     */
    public static boolean isValidNric(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.nric;
    }

    @Override
    public int hashCode() {
        return this.nric.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return this == other || (other instanceof Nric && this.nric.equals(((
                Nric) other).nric));
    }
}
