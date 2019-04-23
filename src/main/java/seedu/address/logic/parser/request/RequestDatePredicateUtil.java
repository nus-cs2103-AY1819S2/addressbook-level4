package seedu.address.logic.parser.request;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;

import seedu.address.model.request.RequestDate;

/**
 * Parses of date keywords and returns a valid date list
 *
 * @@author daviddl9
 */
public class RequestDatePredicateUtil {
    private static final int VALID_DATE_LIST_SIZE = 2;

    private static final SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    /**
     * Sorts the dates in ascending request
     * Returns a list of size 2 if more than 2 date fields are specified
     */
    public List<Date> parseDateKeywords(List<String> keywords) throws ParseException {
        final int firstIndex = 0;
        final int lastIndex = keywords.size() - 1;

        List<Date> dates = parseStringToDate(keywords);

        Collections.sort(dates);

        if (dates.size() > VALID_DATE_LIST_SIZE) {
            List<Date> newKeywords = new ArrayList<>();
            newKeywords.add(dates.get(firstIndex));
            newKeywords.add(dates.get(lastIndex));
            return newKeywords;
        }

        return dates;
    }

    /**
     * Parses a list of {@code stringDates} and returns a list of Date object
     * @throws ParseException if invalid date format is supplied
     */
    private List<Date> parseStringToDate(List<String> stringsDates) throws ParseException {
        List<Date> dates = new ArrayList<>();

        for (String stringDate : stringsDates) {
            try {
                sf.setLenient(false);
                dates.add(sf.parse(stringDate));
            } catch (java.text.ParseException pE) {
                throw new ParseException(RequestDate.MESSAGE_CONSTRAINTS);
            }
        }

        return dates;
    }
}
