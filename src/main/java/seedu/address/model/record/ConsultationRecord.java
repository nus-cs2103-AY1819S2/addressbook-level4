package seedu.address.model.record;

import java.math.BigDecimal;

import seedu.address.model.consultation.Prescription;

/**
 * Record representation of a consultation
 */
public class ConsultationRecord extends Record {
    private Prescription prescription;
    public ConsultationRecord(Prescription prescription) {
        this.prescription = prescription;
    }
    @Override
    public Statistics toStatistics() {
        BigDecimal consultationFee = Statistics.getConsultationFee();
        int quantity = prescription.getQuantity();
        // TODO BigDecimal prescriptionFee = quantity * prescription.getMedicine().getPrice();
        return new Statistics(1, consultationFee, BigDecimal.ZERO);
    }
}
