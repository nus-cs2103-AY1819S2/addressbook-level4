package seedu.address.model.record;

/**
 * s
 */
public class ConsultationRecord extends Record {
    @Override
    public void record() {

    }

    @Override
    public Statistics toStatistics() {
        return Statistics.fromConsultationRecord(this);
    }
}
