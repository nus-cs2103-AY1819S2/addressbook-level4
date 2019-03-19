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
        BigDecimal medicinePrice = prescription.getMedicine().getPrice();
        BigDecimal prescriptionFee;
        if (medicinePrice == null) {
            prescriptionFee = BigDecimal.ZERO;
        } else {
            prescriptionFee = medicinePrice.multiply(new BigDecimal(quantity));
        }
        // BigDecimal prescriptionFee = prescription.getMedicine().getPrice().multiply(new BigDecimal(quantity));
        return new Statistics(1, consultationFee.add(prescriptionFee), BigDecimal.ZERO);
    }
}
