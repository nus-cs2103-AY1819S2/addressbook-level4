package seedu.address.model.record;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import seedu.address.model.consultation.Prescription;
import seedu.address.model.medicine.Medicine;

class ConsultationRecordTest {
    @Test
    void toStatistics() {
        Medicine medicine = new Medicine("test");
        medicine.setPrice(BigDecimal.valueOf(22.20));
        Prescription prescription = new Prescription(medicine, 3);
        BigDecimal prescriptionFee = prescription.getMedicine().getPrice()
                .multiply(new BigDecimal(prescription.getQuantity()));
        Statistics stats = new Statistics(1,
                Statistics.getConsultationFee().add(prescriptionFee), BigDecimal.ZERO);
        ConsultationRecord consultRecord = new ConsultationRecord(prescription);
        Assert.assertEquals(consultRecord.toStatistics(), stats);
    }
}
