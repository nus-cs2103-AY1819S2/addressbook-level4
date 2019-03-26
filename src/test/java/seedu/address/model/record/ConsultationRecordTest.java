package seedu.address.model.record;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import seedu.address.model.consultation.Assessment;
import seedu.address.model.consultation.Diagnosis;
import seedu.address.model.consultation.Prescription;
import seedu.address.model.consultation.Symptom;
import seedu.address.model.medicine.Medicine;

class ConsultationRecordTest {
    @Test
    void toStatistics() {
        RecordManager recordManager = new RecordManager();

        Medicine medicine = new Medicine("test");
        medicine.setPrice(BigDecimal.valueOf(22.20));
        Prescription prescription = new Prescription(medicine, 3);
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription);

        Assessment testAssessment = new Assessment("Test");
        ArrayList <Symptom> testSymptoms = new ArrayList<>();
        testSymptoms.add(new Symptom("Vomitting blood"));

        Diagnosis diagnosis = new Diagnosis(testAssessment, testSymptoms);

        BigDecimal prescriptionFee = prescription.getMedicine().getPrice()
                .multiply(new BigDecimal(prescription.getQuantity()));

        HashMap<String, Integer> medicinesCount = new HashMap<>();
        HashMap<String, Integer> symptomsCount = new HashMap<>();
        medicinesCount.put(prescription.getMedicine().name, prescription.getQuantity());
        symptomsCount.put("Vomitting blood", 1);

        Statistics stats = new Statistics(1, recordManager.getConsultationFee().add(prescriptionFee), BigDecimal.ZERO,
                medicinesCount, symptomsCount);

        ConsultationRecord consultRecord = new ConsultationRecord(prescriptions, diagnosis);
        Assert.assertEquals(consultRecord.toStatistics(recordManager), stats);
    }
}
