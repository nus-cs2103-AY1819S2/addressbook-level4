package quickdocs.model.consultation;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

public class DiagnosisTest {

    @Test
    public void constructor_validDiagnosis_success() {

        Assessment testAssessment = new Assessment("Test");
        ArrayList <Symptom> testSymptoms = new ArrayList<>();
        quickdocs.testutil.Assert.assertThrows(
                IllegalArgumentException.class, () -> new Diagnosis(testAssessment, testSymptoms));

        testSymptoms.add(new Symptom("Vomitting blood"));

        Assessment testAssessment2 = new Assessment("Test");
        ArrayList <Symptom> testSymptoms2 = new ArrayList<>();
        testSymptoms2.add(new Symptom("Vomitting blood"));
        Diagnosis testDiagnosis = new Diagnosis(testAssessment2, testSymptoms2);

        Assert.assertEquals(testDiagnosis, new Diagnosis(testAssessment, testSymptoms));
    }

    @Test
    public void equals() {
        Assessment testAssessment = new Assessment("Test");
        ArrayList <Symptom> testSymptoms = new ArrayList<>();
        testSymptoms.add(new Symptom("Vomitting blood"));

        Diagnosis diagnosis1 = new Diagnosis(testAssessment, testSymptoms);
        Assert.assertTrue(diagnosis1.equals(diagnosis1));

        Assessment testAssessment2 = new Assessment("Test");
        ArrayList <Symptom> testSymptoms2 = new ArrayList<>();
        testSymptoms2.add(new Symptom("Vomitting blood"));

        Diagnosis diagnosis2 = new Diagnosis(testAssessment2, testSymptoms2);
        Assert.assertTrue(diagnosis1.equals(diagnosis2));

        Assessment testAssessment3 = new Assessment("Test");
        ArrayList <Symptom> testSymptoms3 = new ArrayList<>();
        testSymptoms3.add(new Symptom("Coughing blood"));

        Diagnosis diagnosis3 = new Diagnosis(testAssessment3, testSymptoms3);
        Assert.assertFalse(diagnosis1.equals(diagnosis3));
    }

}
