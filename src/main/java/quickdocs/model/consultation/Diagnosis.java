package quickdocs.model.consultation;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents a diagnosis given to the patient during a consultation session
 */
public class Diagnosis {

    private Assessment assessment;
    private ArrayList<Symptom> symptoms;

    public Diagnosis() {
    }

    public Diagnosis(Assessment assessment, ArrayList<Symptom> symptoms) {

        if (symptoms.size() < 1) {
            throw new IllegalArgumentException("Each diagnosis must have symptoms\n");
        }

        this.assessment = assessment;
        this.symptoms = symptoms;
    }

    public Assessment getAssessment() {
        return assessment;
    }

    public ArrayList<Symptom> getSymptoms() {
        return symptoms;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Diagnosis\n");
        sb.append("====================\n");
        sb.append("Assessment: " + assessment + "\n");
        sb.append("Symptoms:\n");
        for (int i = 0; i < symptoms.size(); i++) {
            sb.append(i + 1 + ") " + symptoms.get(i).getSymptom() + "\n");
        }
        return sb.toString();
    }

    // for testing
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Diagnosis // instanceof handles nulls
                && assessment.equals(((Diagnosis) other).getAssessment())
                && Arrays.equals(
                symptoms.toArray(), ((Diagnosis) other).getSymptoms().toArray())); // state check
    }
}
