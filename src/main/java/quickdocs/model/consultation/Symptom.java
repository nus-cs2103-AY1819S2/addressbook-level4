package quickdocs.model.consultation;

/**
 * Represents a single symptom of a patient's diagnosis
 */
public class Symptom {
    public static final String REGEX_SYMPTOMS = "[\\p{Alnum}][\\p{Alnum} ]*";
    public static final String SYMPTOMS_CONSTRAINTS =
            "Symptoms should only contain alphanumeric characters and spaces but not blank";

    private String symptom;

    public Symptom() {
    }

    public Symptom(String symptom) {
        if (!symptom.matches(REGEX_SYMPTOMS)) {
            throw new IllegalArgumentException(SYMPTOMS_CONSTRAINTS);
        }
        this.symptom = symptom;
    }

    public String getSymptom() {
        return symptom;
    }

    @Override
    public String toString() {
        return symptom;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Symptom // instanceof handles nulls
                && symptom.equals(((Symptom) other).getSymptom())); // state check
    }
}
