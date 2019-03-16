package seedu.address.model.record;

import seedu.address.model.datetime.DateBase;

/**
 * Represents the date the record is created.
 */
public class RecordDate extends DateBase {
    /**
     * Default constructor. Sets date to current date.
     */
    RecordDate() {
        DateBase today = DateBase.getToday();
        setTo(today.getDay(), today.getMonth(), today.getYear());
    }
}
