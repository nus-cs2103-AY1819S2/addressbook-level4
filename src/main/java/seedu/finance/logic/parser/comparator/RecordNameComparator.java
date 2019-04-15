package seedu.finance.logic.parser.comparator;

import java.util.Comparator;

import seedu.finance.model.record.Record;

/**
 * A comparison function to sort the records by name in lexicographical order (case insensitive).
 */
public class RecordNameComparator implements Comparator<Record> {
    @Override
    public int compare(Record r1, Record r2) {
        return r1.getName().toString().compareToIgnoreCase(r2.getName().toString());
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
