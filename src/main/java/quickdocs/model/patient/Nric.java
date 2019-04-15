package quickdocs.model.patient;

/**
 * Represents NRIC of the patient record
 */
public class Nric {
    public static final String REGEX_NRIC1 = "^[ST]\\d{7}[ABCDEFGHIZJ]";
    public static final String REGEX_NRIC2 = "^[FG]\\d{7}[KLMNPQRTUWX]$";
    public static final String NRIC_CONSTRAINTS =
            "NRIC starts with S,T,U or G followed by 7 digits and must end with a valid letter\n"
        + "For NRIC starting with S or T, they can only end with these letters: ABCDEFGHIZJ\n"
        + "For NRIC starting with F or G, they can only end with these letters: KLMNPQRTUWX\n";

    private String nric;

    // empty constructor for json reconstruction
    public Nric() {
    }

    public Nric(String nric) {
        if (!nric.matches(REGEX_NRIC1) && !nric.matches(REGEX_NRIC2)) {
            throw new IllegalArgumentException(NRIC_CONSTRAINTS);
        }
        this.nric = nric;
    }

    public String getNric() {
        return nric;
    }

    @Override
    public String toString() {
        return nric;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nric // instanceof handles nulls
                && nric.equals(((Nric) other).getNric())); // state check
    }
}
