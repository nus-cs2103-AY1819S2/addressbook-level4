package quickdocs.model.consultation;

/**
 * Represents the assessment of the illness during a consultation session
 */
public class Assessment {
    public static final String REGEX_SYMPTOMS = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String SYMPTOMS_CONSTRAINTS =
            "Assessment should only contain alphanumeric characters and spaces but not blank";

    private String assessment;

    public Assessment() {
    }

    public Assessment(String assessment) {
        if (!assessment.matches(REGEX_SYMPTOMS)) {
            throw new IllegalArgumentException(SYMPTOMS_CONSTRAINTS);
        }
        this.assessment = assessment;
    }

    public String getAssessment() {
        return assessment;
    }

    @Override
    public String toString() {
        return assessment;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Assessment // instanceof handles nulls
                && assessment.equals(((Assessment) other).getAssessment())); // state check
    }
}
