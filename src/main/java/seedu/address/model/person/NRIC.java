package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's NRIC to be represented.
 * Guarantees Non-Null and is valid as declared in {@link #isValidNRIC(String)}
 */
public class NRIC {

    public static final String MESSAGE_CONSTRAINTS = "NRIC should start with " +
            "an S or T, followed by 7 numeric characters and then a letter. " +
            "All letters are to be in capital casing";
    public static final String VALIDATION_REGEX = "[ST]\\d{7}[A-Z]"; // NRIC
    // should start with capital S/T, followed by 7 numeric characters and a
    // capital letter.

    private String nric;

    /**
     * Constructs a {@code NRIC}.
     *
     * @param nric A valid NRIC.
     */
    public NRIC(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNRIC(nric), MESSAGE_CONSTRAINTS);
        this.nric = nric;
    }

    public String getNric() {
        return nric;
    }

    /**
     * Returns true if the NRIC entered matches is valid.
     * @param test NRIC string to be tested
     * @return true if test matches the validation regex
     */
    public boolean isValidNRIC(String test) {
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
        return this == other || (other instanceof NRIC && this.nric.equals(
                ((NRIC) other).nric));
    }
}
