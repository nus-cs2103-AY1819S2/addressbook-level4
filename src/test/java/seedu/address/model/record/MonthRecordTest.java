package seedu.address.model.record;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.consultation.Assessment;
import seedu.address.model.consultation.Diagnosis;
import seedu.address.model.consultation.Prescription;
import seedu.address.model.consultation.Symptom;
import seedu.address.model.medicine.Medicine;

class MonthRecordTest {
    private RecordManager recordManager;
    private MonthRecord monthRecord;
    private Record consultationRecord;
    private Record medicinePurchaseRecord;
    private Diagnosis diagnosis;
    private HashMap<String, Integer> medicinesCount;
    private HashMap<String, Integer> symptomsCount;

    @BeforeEach
    void setUp() {
        recordManager = new RecordManager();
        Medicine medicine = new Medicine("test");
        Prescription prescription = new Prescription(medicine, 1);
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription);
        Assessment testAssessment = new Assessment("Test");
        ArrayList <Symptom> testSymptoms = new ArrayList<>();
        testSymptoms.add(new Symptom("Vomitting blood"));
        diagnosis = new Diagnosis(testAssessment, testSymptoms);
        medicinesCount = new HashMap<>();
        symptomsCount = new HashMap<>();
        medicinesCount.put(prescription.getMedicine().name, prescription.getQuantity());
        symptomsCount.put("Vomitting blood", 1);
        monthRecord = new MonthRecord();
        consultationRecord = new ConsultationRecord(prescriptions, diagnosis);
        medicinePurchaseRecord = new MedicinePurchaseRecord(medicine, 1, BigDecimal.valueOf(10.00));
    }


    @Test
    void getStatistics() {
        monthRecord.addRecord(consultationRecord, recordManager);
        monthRecord.addRecord(medicinePurchaseRecord, recordManager);
        Statistics stats = new Statistics(1, BigDecimal.valueOf(30.00), BigDecimal.valueOf(10.00),
                medicinesCount, symptomsCount);
        Assert.assertEquals(monthRecord.getStatistics(), stats);
    }

    @Test
    void addRecord() {
        monthRecord.addRecord(consultationRecord, recordManager);
        monthRecord.addRecord(medicinePurchaseRecord, recordManager);
        Assert.assertEquals(monthRecord.getNoOfRecords(), 2);
    }
}
