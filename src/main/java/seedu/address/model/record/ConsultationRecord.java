package seedu.address.model.record;

import java.math.BigDecimal;

/**
 * Record representation of a consultation
 */
public class ConsultationRecord extends Record {
    // TODO Constructor
    @Override
    public Statistics toStatistics() {
        BigDecimal consultationFee = Statistics.getConsultationFee();
        // TODO Add prescription expenditures
        return new Statistics(1, consultationFee, BigDecimal.ZERO);
    }
}
