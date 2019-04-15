package quickdocs.model.record;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import quickdocs.model.consultation.Diagnosis;
import quickdocs.model.consultation.Prescription;
import quickdocs.model.consultation.Symptom;

/**
 * Record representation of a consultation
 */
public class ConsultationRecord extends Record {

    private ArrayList<Prescription> prescriptions;
    private Diagnosis diagnosis;

    public ConsultationRecord(ArrayList<Prescription> prescription, Diagnosis diagnosis) {
        this.prescriptions = prescription;
        this.diagnosis = diagnosis;
    }

    @Override
    public Statistics toStatistics(StatisticsManager statisticsManager) {
        BigDecimal consultationFee = statisticsManager.getConsultationFee();
        BigDecimal prescriptionFee = BigDecimal.ZERO;
        HashMap<String, Integer> medicinesCount = new HashMap<>();

        for (Prescription prescription : prescriptions) {
            int quantity = prescription.getQuantity();
            medicinesCount.put(prescription.getMedicine().name, quantity);
            BigDecimal medicinePrice = prescription.getMedicine().getPrice();
            if (medicinePrice != null) {
                prescriptionFee = prescriptionFee.add(medicinePrice.multiply(new BigDecimal(quantity)));
            }
        }

        HashMap<String, Integer> symptomsCount = new HashMap<>();
        for (Symptom symptom : diagnosis.getSymptoms()) {
            symptomsCount.put(symptom.toString(), 1);
        }

        return new Statistics(1, consultationFee.add(prescriptionFee), BigDecimal.ZERO,
                medicinesCount, symptomsCount);
    }
}
