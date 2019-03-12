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
        Statistics stats = new Statistics(1, Statistics.getConsultationFee(), BigDecimal.ZERO);
        Prescription prescription = new Prescription(medicine, 3);
        ConsultationRecord consultRecord = new ConsultationRecord(prescription);
        Assert.assertEquals(consultRecord.toStatistics(), stats);
    }
}
