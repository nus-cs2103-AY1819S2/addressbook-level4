package seedu.address.model.record;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Represents a procedure of a record.
 * Guarantees: immutable; is valid as declared in {@link #isValidProcedure(String)}
 */
public class Procedure {
    public static final String[] PROCEDURE_LIST = new String[] {"consultation", "preventive", "filling", "crown",
        "gum", "extraction", "replacement", "braces", "aesthetic", "implant", "other"};

    /*
     * The first character of the description must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public static final String MESSAGE_CONSTRAINTS = "Procedures should be in the format of TYPE-SUBTYPE\n"
        + "TYPE must be one of the following options (case insensitive): Consultation, Preventive, Filling, Crown\n"
        + "Gum, Extraction, Replacement, Braces, Aesthetic, Implant, Other\n"
        + "Procedures must be divided by a -, and only contain alphanumeric"
        + " characters, and it should not be blank";

    private static final Set<String> procedureSet = new HashSet<>(Arrays.asList(PROCEDURE_LIST));

    private final String procedureType;
    private final String typeDetails;

    /**
     * Constructs a {@code Procedure}
     *
     * @param procedure A valid procedure
     */
    public Procedure(String procedure) {
        requireNonNull(procedure);
        checkArgument(isValidProcedure(procedure), MESSAGE_CONSTRAINTS);
        String[] strSplit = procedure.split("-");
        this.procedureType = strSplit[0];
        this.typeDetails = strSplit[1];
    }

    /**
     * Returns true if a given string is a valid procedure.
     */
    public static boolean isValidProcedure (String test) {
        String[] strSplit = test.split("-");
        if (strSplit.length != 2) {
            return false;
        }
        return procedureSet.contains(strSplit[0].toLowerCase().trim()) && strSplit[1].matches(VALIDATION_REGEX);
    }

    public String getProcedureType() {
        return this.procedureType;
    }

    public String getTypeDetails() {
        return this.procedureType;
    }

    @Override
    public String toString() {
        return procedureType + "-" + typeDetails;
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
