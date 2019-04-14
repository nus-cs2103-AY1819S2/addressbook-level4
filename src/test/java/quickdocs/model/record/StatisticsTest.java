package quickdocs.model.record;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import quickdocs.model.consultation.Assessment;
import quickdocs.model.consultation.Diagnosis;
import quickdocs.model.consultation.Prescription;
import quickdocs.model.consultation.Symptom;
import quickdocs.model.medicine.Medicine;
import quickdocs.testutil.Assert;

class StatisticsTest {
    private StatisticsManager statisticsManager;
    private Statistics stats;
    private Record record1;
    private Record record2;

    /**
     * Setup before each test
     */
    @BeforeEach
    public void init() {
        statisticsManager = new StatisticsManager();
        Medicine medicine = new Medicine("test");
        Prescription prescription = new Prescription(medicine, 1);
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription);
        Assessment testAssessment = new Assessment("Test");
        ArrayList <Symptom> testSymptoms = new ArrayList<>();
        testSymptoms.add(new Symptom("Vomitting blood"));
        Diagnosis diagnosis = new Diagnosis(testAssessment, testSymptoms);
        record1 = new ConsultationRecord(prescriptions, diagnosis);
        record2 = new MedicinePurchaseRecord(medicine, 1, BigDecimal.valueOf(10.00));
        HashMap<String, Integer> medicinesCount = new HashMap<>();
        HashMap<String, Integer> symptomsCount = new HashMap<>();
        medicinesCount.put(prescription.getMedicine().name, prescription.getQuantity());
        symptomsCount.put("Vomitting blood", 1);
        stats = new Statistics(1, BigDecimal.valueOf(30.00), BigDecimal.valueOf(10.00), medicinesCount, symptomsCount);
    }

    @Test
    void constructor_negativeNoOfConsultations_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Statistics(-1, BigDecimal.ZERO, BigDecimal.ZERO));
    }

    @Test
    void constructor_negativeBigDecimalValues_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () ->
                new Statistics(0, BigDecimal.valueOf(-1), BigDecimal.valueOf(-999)));
    }

    @Test
    void constructor_nullHashMaps_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Statistics(0, BigDecimal.ZERO, BigDecimal.ZERO, null, null));
    }

    @Test
    void merge() {
        Statistics testStats = record1.toStatistics(statisticsManager).merge(record2.toStatistics(statisticsManager));
        assertEquals(stats, testStats);
    }

    @Test
    void toStringTest() {
        StringBuilder sb = new StringBuilder();
        sb.append("Number of consultations: ")
                .append(1)
                .append("\n\n")
                .append("Most common medicine prescribed: \n")
                .append("test: 1\n")
                .append("\n")
                .append("Most common symptom diagnosed: \n")
                .append("Vomitting blood: 1\n")
                .append("\n")
                .append("Revenue: ")
                .append(Statistics.currencyFormat(BigDecimal.valueOf(30.00)))
                .append("\n")
                .append("Expenditure: ")
                .append(Statistics.currencyFormat(BigDecimal.valueOf(10.00)))
                .append("\n")
                .append("Profit: ")
                .append(Statistics.currencyFormat(BigDecimal.valueOf(20.00)))
                .append("\n\n");
        assertEquals(stats.toString(), sb.toString());
    }

    @Test
    void getMostCommonKeyFromHashMap_nullHashMap_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> Statistics.getMostCommonKeyFromHashMap(null));
    }

    @Test
    void getMostCommonKeyFromHashMap_hashMapWith1Key_success() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("paracetamol", 1);
        String expectedResult = "paracetamol: 1\n";
        assertEquals(expectedResult, Statistics.getMostCommonKeyFromHashMap(hashMap));
    }
}
