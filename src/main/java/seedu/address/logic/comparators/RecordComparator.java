package seedu.address.logic.comparators;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.record.Record;

import java.util.Comparator;

/**
 * Contains comparators for each record attribute
 */
public class RecordComparator {

    public static Comparator<Record> getRecordComparator(String parameterType) throws ParseException {
        Comparator<Record> recComp;

        switch (parameterType.trim()) {

        case "date":
            break;

        case "desc":
            break;

        default:
            throw new ParseException("Unknown parameter type");
        }

        return recComp;
    }
}
