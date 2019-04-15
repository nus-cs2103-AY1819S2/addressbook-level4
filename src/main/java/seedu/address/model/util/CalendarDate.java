package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;

import seedu.address.logic.parser.CliSyntax;

/**
 * Contains methods for checking if a input date is valid according to the calendar year.
 */
public class CalendarDate {

    /**
     * Message constraints.
     */
    public static final String DAY_MONTH_CONSTRAINTS = "The selected month does not have the chosen day or "
        + "the selected month does not exist in the calendar.";

    /**
     * Constants to represent different fields.
     */
    private static final int DAY_FIELD = 0;
    private static final int MONTH_FIELD = 1;
    private static final int YEAR_FIELD = 2;

    /**
     * Constants to represent first and last month values
     */
    private static final int FIRST_DAY_MONTH = 1;
    private static final int LAST_MONTH = 12;
    private static final int NEXT = 1;

    /**
     * HashMap mapping the key(month) to the value (number of days in the month).
     */
    private static HashMap<Integer, Integer> daysInMonth;

    /**
     * Month names and days in each month.
     */
    private static final int JANUARY = 1;
    private static final int JAN_MAX_DAYS = 31;
    private static final int FEBRUARY = 2;
    private static final int FEB_MAX_DAYS = 29;
    private static final int MARCH = 3;
    private static final int MAR_MAX_DAYS = 31;
    private static final int APRIL = 4;
    private static final int APR_MAX_DAYS = 30;
    private static final int MAY = 5;
    private static final int MAY_MAX_DAYS = 31;
    private static final int JUNE = 6;
    private static final int JUNE_MAX_DAYS = 30;
    private static final int JULY = 7;
    private static final int JUL_MAX_DAYS = 31;
    private static final int AUGUST = 8;
    private static final int AUG_MAX_DAYS = 31;
    private static final int SEPTEMBER = 9;
    private static final int SEP_MAX_DAYS = 30;
    private static final int OCTOBER = 10;
    private static final int OCT_MAX_DAYS = 31;
    private static final int NOVEMBER = 11;
    private static final int NOV_MAX_DAYS = 30;
    private static final int DECEMBER = 12;
    private static final int DEC_MAX_DAYS = 31;

    /**
     * Standard number of days in a leap and non-leap year.
     */
    private static final int LEAP_YEAR_DAYS = 366;
    private static final int NON_LEAP_YEAR_DAYS = 365;

    /**
     * Static initializer for Calendar Dates In Month
     */
    static {
        daysInMonth = new HashMap<>();

        daysInMonth.put(JANUARY, JAN_MAX_DAYS);
        daysInMonth.put(FEBRUARY, FEB_MAX_DAYS);
        daysInMonth.put(MARCH, MAR_MAX_DAYS);
        daysInMonth.put(APRIL, APR_MAX_DAYS);
        daysInMonth.put(MAY, MAY_MAX_DAYS);
        daysInMonth.put(JUNE, JUNE_MAX_DAYS);
        daysInMonth.put(JULY, JUL_MAX_DAYS);
        daysInMonth.put(AUGUST, AUG_MAX_DAYS);
        daysInMonth.put(SEPTEMBER, SEP_MAX_DAYS);
        daysInMonth.put(OCTOBER, OCT_MAX_DAYS);
        daysInMonth.put(NOVEMBER, NOV_MAX_DAYS);
        daysInMonth.put(DECEMBER, DEC_MAX_DAYS);
    }

    /**
     * Check if the date is valid.
     * @param dateInput the input by user.
     * @return true if date is valid, false otherwise.
     */

    public static boolean isValidDayInMonth (String dateInput) {

        Integer[] date = PocketProjectDate.splitComponents(dateInput);

        if (date[MONTH_FIELD] < FIRST_DAY_MONTH || date[MONTH_FIELD] > LAST_MONTH) {
            return false;
        }

        int maxDays = daysInMonth.get(date[MONTH_FIELD]);
        if (date[DAY_FIELD] < FIRST_DAY_MONTH || date[DAY_FIELD] > maxDays) {
            return false;
        }

        if (date[MONTH_FIELD] == FEBRUARY && date[DAY_FIELD] == FEB_MAX_DAYS) {
            if (!isLeapYear(date[YEAR_FIELD])) {
                return false;
            }
        }
        return true;
    }

    /**
     * Inputted is reformats the flexible date input to DD/MM/YYYY so that input can be checked to see
     * if it is a valid day in the month.
     * @param keyword indicates whether this, next or last month.
     * @param dayOfMonth the target day of the month.
     *
     * @return if it is a valid day in the selected month.
     */
    public static boolean isValidDayInMonth(String keyword, int dayOfMonth) {

        LocalDateTime localDateTime = LocalDateTime.now();
        int currentYear = localDateTime.getYear();
        int currentMonth = localDateTime.getMonth().getValue();

        if (keyword.equals(CliSyntax.PREFIX_CURRENT.toString())) {
            return isValidDayInMonth(PocketProjectDate.generateStringDateFormat(dayOfMonth, currentMonth, currentYear));
        } else if (keyword.equals(CliSyntax.PREFIX_FUTURE.toString())) {
            return isValidDayInMonth(PocketProjectDate.generateStringDateFormat(dayOfMonth, (currentMonth + NEXT)
                 % LAST_MONTH, currentYear));
        } else {
            return isValidDayInMonth(PocketProjectDate.generateStringDateFormat(dayOfMonth, (currentMonth - NEXT)
                 % LAST_MONTH, currentYear));
        }
    }

    /**
     * This method checks whether a year is leap or not by checking the number of days in the
     * year exceed 365 days. Leap years have 366 days in a year.
     *
     * @param year the target year.
     * @return true is leap year and false otherwise.
     */
    public static boolean isLeapYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);

        int numOfDays = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);

        if (numOfDays == LEAP_YEAR_DAYS) {
            return true;
        }
        return false;
    }
}
