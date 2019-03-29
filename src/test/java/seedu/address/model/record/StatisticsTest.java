package seedu.address.model.record;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import seedu.address.model.consultation.Assessment;
import seedu.address.model.consultation.Diagnosis;
import seedu.address.model.consultation.Prescription;
import seedu.address.model.consultation.Symptom;
import seedu.address.model.medicine.Medicine;

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
    void merge() {
        Statistics testStats = record1.toStatistics(statisticsManager).merge(record2.toStatistics(statisticsManager));
        Assert.assertEquals(testStats, stats);
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
        Assert.assertEquals(stats.toString(), sb.toString());
    }
}
