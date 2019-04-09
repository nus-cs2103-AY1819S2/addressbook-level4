package seedu.travel.model.place;

import static java.util.Objects.requireNonNull;

import static seedu.travel.commons.util.AppUtil.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the date that the place was visited in TravelBuddy.
 * Guarantees: immutable; is valid as declared in {@link #isValidDateVisited(String)}
 */
public class DateVisited {

    public static final String MESSAGE_CONSTRAINTS_SEARCH =
            "Years should only contain a year from 1900 to the current year";
    public static final String MESSAGE_INCORRECT_FORMAT = "Date visited must follow the DD/MM/YYYY format.";
    public static final String MESSAGE_FUTURE_DATE_ADDED = "Date visited must be a present or past date. It must also"
        + " be after the year 1900.";
    public static final String MESSAGE_DATE_DOES_NOT_EXIST = "Date does not exist.";
    public static final Integer YEAR_MINIMUM = 1900;
    private static final String DATE_FORMAT_CHECK_PATTERN = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
    private static final String DATE_EXISTENCE_CHECK_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)"
        + "\\d\\d)";
    private final String date;
    private final String year;

    /**
    * Constructs a {@code DateVisited}.
    *
    * @param strDateVisited A valid date visited.
    */
    public DateVisited(String strDateVisited) {
        requireNonNull(strDateVisited);
        checkArgument(isCorrectDateFormat(strDateVisited), MESSAGE_INCORRECT_FORMAT);
        checkArgument(doesDateExist(strDateVisited), MESSAGE_DATE_DOES_NOT_EXIST);
        checkArgument(isValidDateVisited(strDateVisited), MESSAGE_FUTURE_DATE_ADDED);
        this.date = strDateVisited;
        this.year = date.substring(date.length() - 4);
    }

    /**
     * Returns the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Returns the year
     */
    public String getYear() {
        return year;
    }

    /**
     * Returns true if a given string is in the correct date format.
     */
    public static boolean isCorrectDateFormat(String strDate) {
        if (!(strDate instanceof String)) {
            throw new java.lang.NullPointerException();
        }

        if (strDate.trim().equals("")) {
            return false;
        }

        if (strDate.length() > 10) {
            return false;
        }

        Pattern pattern = Pattern.compile(DATE_FORMAT_CHECK_PATTERN);
        Matcher matcher = pattern.matcher(strDate);

        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if a given string is a date that exists.
     */
    public static boolean doesDateExist(String strDate) {
        Pattern pattern = Pattern.compile(DATE_EXISTENCE_CHECK_PATTERN);
        Matcher matcher = pattern.matcher(strDate);

        if (matcher.matches()) {
            matcher.reset();
            if (matcher.find()) {
                String dd = matcher.group(1);
                String mm = matcher.group(2);
                int yy = Integer.parseInt(matcher.group(3));

                // April, June, November & September does not have 31 days.
                if (dd.equals("31") && (mm.equals("4") || mm.equals("6") || mm.equals("9")
                    || mm.equals("11") || mm.equals("04") || mm.equals("06") || mm.equals("09"))) {
                    return false;
                } else if (mm.equals("2") || mm.equals("02")) {
                    // Check if it is leap year.
                    if (yy % 4 == 0) {
                        if (dd.equals("30") || dd.equals("31")) {
                            return false;
                        }
                    } else {
                        if (dd.equals("29") || dd.equals("30") || dd.equals("31")) {
                            // Feb does not have 29, 30 or 31 as a date.
                            return false;
                        }
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        
        return true;
    }

    /**
     * Returns true if a given string is a present or past date after 1900.
     */
    public static boolean isValidDateVisited(String strDate) {
        if (!(strDate instanceof String)) {
            throw new java.lang.NullPointerException();
        }

        Date todayDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dateVisit;

        try {
            dateVisit = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            // format is incorrect.
            return false;
        }

        String noPersonBornBeforeThisDate = "01/01/1900";
        Date birthDate;
        try {
            birthDate = simpleDateFormat.parse(noPersonBornBeforeThisDate);
        } catch (ParseException e) {
            // format is incorrect.
            return false;
        }

        if (dateVisit.after(todayDate) || dateVisit.before(birthDate)) {
            return false;
        }

        if (todayDate.before(birthDate)) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if a given string is a year that is after 1900 and before current year
     */
    public static boolean isValidYear(String strDate) {
        if (!(strDate instanceof String)) {
            throw new java.lang.NullPointerException();
        }

        LocalDateTime currentTime = LocalDateTime.now();
        int maxYear = currentTime.getYear();
        Integer queryYear;

        // checks if input string is an integer
        try {
            queryYear = Integer.parseInt(strDate);
        } catch (NumberFormatException nfe) {
            return false;
        }

        // checks if input year is within the range of 1900-current year
        if (queryYear < YEAR_MINIMUM || queryYear > maxYear) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof DateVisited // instanceof handles nulls
            && date.equals(((DateVisited) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
