package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a procedure of a record.
 * Guarantees: immutable; is valid as declared in {@link #isValidProcedure(String)}
 */
public class Procedure {

    public static final String MESSAGE_CONSTRAINTS = "Procedures should only contain alphanumeric characters, and it "
        + "should not be blank";

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String procedureType;

    /**
     * Constructs a {@code Procedure}
     *
     * @param procedure A valid procedure
     */
    public Procedure(String procedure) {
        requireNonNull(procedure);
        checkArgument(isValidProcedure(procedure), MESSAGE_CONSTRAINTS);
        procedureType = procedure;
    }

    /**
     * Returns true if a given string is a valid procedure.
     */
    public static boolean isValidProcedure (String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return procedureType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Procedure // instanceof handles nulls
            && procedureType.equals(((Procedure) other).procedureType)); // state check
    }

    @Override
    public int hashCode() {
        return procedureType.hashCode();
    }
}
