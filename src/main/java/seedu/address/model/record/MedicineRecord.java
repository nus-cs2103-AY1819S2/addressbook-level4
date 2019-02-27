package seedu.address.model.record;

/**
 * s
 */
public class MedicineRecord extends Record {
    @Override
    public void record() {

    }
    @Override
    public Statistics toStatistics() {
        return Statistics.fromMedicineRecord(this);
    }
}
