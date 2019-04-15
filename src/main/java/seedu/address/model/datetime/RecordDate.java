package seedu.address.model.datetime;

/**
 * Represents the date the record is created.
 */
public class RecordDate extends DateBase {
    /**
     * Default constructor. Sets date to current date.
     */
    public RecordDate() {
        DateBase today = DateBase.getToday();
        setTo(today.getDay(), today.getMonth(), today.getYear());
    }

    public RecordDate(String date) {
        super(date);
    }
}
