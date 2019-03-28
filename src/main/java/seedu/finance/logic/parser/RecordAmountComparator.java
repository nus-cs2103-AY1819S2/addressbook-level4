package seedu.finance.logic.parser;

import java.util.Comparator;

import seedu.finance.model.record.Record;

/**
 * A comparison function to sort the records by amount in descending order.
 */
public class RecordAmountComparator implements Comparator<Record> {
    @Override
    public int compare(Record r1, Record r2) {
        float diff = Float.parseFloat(r2.getAmount().toString()) - Float.parseFloat(r1.getAmount().toString());
        if (diff < 0) {
            return -1;
        } else if (diff > 0) {
            return 1;
        }
        return 0;
    }
}
