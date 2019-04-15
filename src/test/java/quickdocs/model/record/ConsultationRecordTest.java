package quickdocs.model.record;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import quickdocs.model.consultation.Assessment;
import quickdocs.model.consultation.Diagnosis;
import quickdocs.model.consultation.Prescription;
import quickdocs.model.consultation.Symptom;
import quickdocs.model.medicine.Medicine;
import quickdocs.testutil.TypicalStatistics;

class ConsultationRecordTest {
    @Test
    void toStatistics() {
        StatisticsManager statisticsManager = new StatisticsManager();

        Medicine testMedicine = TypicalStatistics.SAMPLE_MEDICINE_PARACETAMOL;
        testMedicine.setPrice(BigDecimal.valueOf(22.20));

        Prescription prescription = new Prescription(testMedicine, 3);
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription);

        Assessment testAssessment = TypicalStatistics.SAMPLE_ASSESSMENT;
        ArrayList <Symptom> testSymptoms = new ArrayList<>();
        testSymptoms.add(new Symptom("fever"));

        Diagnosis diagnosis = new Diagnosis(testAssessment, testSymptoms);

        BigDecimal prescriptionFee = prescription.getMedicine().getPrice()
                .multiply(new BigDecimal(prescription.getQuantity()));

        HashMap<String, Integer> medicinesCount = new HashMap<>();
        HashMap<String, Integer> symptomsCount = new HashMap<>();
        medicinesCount.put(prescription.getMedicine().name, prescription.getQuantity());
        symptomsCount.put("fever", 1);

        Statistics stats = new Statistics(1,
                statisticsManager.getConsultationFee().add(prescriptionFee), BigDecimal.ZERO,
                medicinesCount, symptomsCount);

        ConsultationRecord consultRecord = new ConsultationRecord(prescriptions, diagnosis);
        Assert.assertEquals(consultRecord.toStatistics(statisticsManager), stats);
    }
}
