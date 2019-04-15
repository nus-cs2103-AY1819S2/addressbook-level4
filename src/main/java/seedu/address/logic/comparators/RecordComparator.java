package seedu.address.logic.comparators;

import java.util.Comparator;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.record.Record;


/**
 * Contains comparators for each record attribute
 */
public class RecordComparator {

    /**
     * Comparator to sort via procedure
     */
    private static Comparator<Record> compRecordProc = new Comparator<Record>() {
        @Override
        public int compare(Record r1, Record r2) {
            return r1.getProcedure().toString().compareTo(r2.getProcedure().toString());
        }
    };

    /**
     * Comparator to sort via record date.
     */
    private static Comparator<Record> compRecordDate = new Comparator<Record>() {
        @Override
        public int compare(Record r1, Record r2) {
            return r1.getRecordDate().compareTo(r2.getRecordDate());
        }
    };

    /**
     * Comparator to sort via description.
     */
    private static Comparator<Record> compRecordDesc = new Comparator<Record>() {
        @Override
        public int compare(Record r1, Record r2) {
            return r1.getDescription().toString().compareTo(r2.getDescription().toString());
        }
    };

    public static Comparator<Record> getRecordComparator(String parameterType) throws ParseException {
        Comparator<Record> recComp;

        switch (parameterType.trim()) {

        case "proc":
            recComp = compRecordProc;
            break;

        case "date":
            recComp = compRecordDate;
            break;

        case "desc":
            recComp = compRecordDesc;
            break;

        default:
            throw new ParseException("Unknown parameter type");
        }

        return recComp;
    }
}
