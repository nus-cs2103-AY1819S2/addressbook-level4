package seedu.finance.logic.parser;

import java.util.Comparator;

import seedu.finance.model.record.Record;

/**
 * A comparison function to sort the records by date with the latest first.
 */
public class RecordDateComparator implements Comparator<Record> {
    @Override
    public int compare(Record r1, Record r2) {
        return r2.getDate().getDate().compareTo(r1.getDate().getDate());
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
