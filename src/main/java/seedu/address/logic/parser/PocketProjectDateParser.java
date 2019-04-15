package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.util.CalendarDate;
import seedu.address.model.util.PocketProjectDate;

/**
 * Parses the flexible date input.
 */
public class PocketProjectDateParser {

    /**
     * Used for initial separation of flexible date command word and args.
     */
    private static final Pattern BASIC_FLEXIDATE_FORMAT = Pattern.compile("(?<keyword>\\S+)(?<arguments>.*)");

    /**
     * To check if input is given in the fixed format.
     */
    private static final String ALPHA_NUMERIC_VALIDATION_REGEX = "^((?!/).)*$";

    /**
     * Used to check if input is a valid integer
     */
    private static final String INTEGER_STRING_FORMAT = "(-?)[0-9]+";

    private static final int FIRST_DAY_OF_WEEK = 1;
    private static final int LAST_DAY_OF_WEEK = 7;
    private static final int FIRST_DAY_OF_MONTH = 1;
    private static final int LAST_DAY_OF_MONTH = 31;

    private static final int NEXT = 1;
    private static final int LAST = -1;
    private static final int CURRENT = 0;
    private static final int DAY_ZERO = 0;

    /**
     * Parses user flexible date input into fixed format string for processing.
     * @param dateInput flexible date input by user
     * @return a date string in the form of DD/MM/YYYY
     * @throws ParseException if the user input does not conform the expected format
     */
    public static String parsePocketProjectDate(String dateInput) throws ParseException {

        final Matcher matcher = BASIC_FLEXIDATE_FORMAT.matcher(dateInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(PocketProjectDate.MESSAGE_CONSTRAINTS);
        }

        final String keyword = matcher.group("keyword").toLowerCase().trim();
        final String arguments = matcher.group("arguments").trim();

        if (keyword.equals(CliSyntax.PREFIX_TODAY.toString())) {
            return formatDate(CURRENT);
        } else if (keyword.equals(CliSyntax.PREFIX_TOMORROW.toString())) {
            return formatDate(NEXT);
        } else if (keyword.equals(CliSyntax.PREFIX_YESTERDAY.toString())) {
            return formatDate(LAST);
        } else if ((keyword.equals(CliSyntax.PREFIX_CURRENT.toString()))
            || (keyword.equals(CliSyntax.PREFIX_FUTURE.toString()))
                || (keyword.equals(CliSyntax.PREFIX_PAST.toString()))) {
            return parseKeyword(keyword, arguments.trim());
        } else {
            throw new ParseException(PocketProjectDate.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * @param keyword they keyword which indicates if it is current (this), past(last) or future(next).
     * @param secondPart the second part of the command input that needs to be parsed.
     * @return a date string in the form of DD/MM/YYYY.
     * @throws ParseException if the user input does not conform the expected format.
     */
    private static String parseKeyword(String keyword, String secondPart) throws ParseException {

        final Matcher matcher = BASIC_FLEXIDATE_FORMAT.matcher(secondPart.trim());
        if (!matcher.matches()) {
            throw new ParseException(PocketProjectDate.MESSAGE_CONSTRAINTS);
        }

        final String weekOrMonth = matcher.group("keyword").toLowerCase();
        final String arguments = matcher.group("arguments");

        if (weekOrMonth.equals(CliSyntax.PREFIX_WEEK.toString())) {
            return formatWeekDate(keyword, arguments.trim());
        } else if (weekOrMonth.equals(CliSyntax.PREFIX_MONTH.toString())) {
            return formatMonthDate(keyword, arguments.trim());
        } else {
            throw new ParseException(PocketProjectDate.FLEXI_DATE_MESSAGE_CONSTRAINTS);
        }
    }

    public static boolean isFlexibleInput(String flexibleDateInput) {
        return flexibleDateInput.matches(ALPHA_NUMERIC_VALIDATION_REGEX);
    }

    private static boolean isValidInput(String input) {
        return input.matches(INTEGER_STRING_FORMAT);
    }

    /**
     * Gets and formats target date based on DD/MM/YYYY
     * @param days no of days of offset from current date.
     * @return formatted target date.
     */
    private static String formatDate(int days) {

        PocketProjectDate date = new PocketProjectDate();
        if (days != DAY_ZERO) {
            return date.dateNumDaysLater(days);
        } else {
            return date.currentDate();
        }
    }

    /**
     * Formats date of week if user choose with reference to this, next or last week.
     * @param keyword this, next or last.
     * @param numberString target day of the week.
     * @return formatted string in the form DD/MM/YYYY
     * @throws ParseException if the user input does not conform the expected format.
     */

    private static String formatWeekDate(String keyword, String numberString) throws ParseException {

        if (!isValidInput(numberString)) {
            throw new ParseException(PocketProjectDate.DAY_OF_WEEK_MONTH_CONSTRAINTS);
        }

        int dayOfWeek = Integer.parseInt(numberString);
        if (dayOfWeek < FIRST_DAY_OF_WEEK || dayOfWeek > LAST_DAY_OF_WEEK) {
            throw new ParseException(PocketProjectDate.DAY_OF_WEEK_MONTH_CONSTRAINTS);
        }

        PocketProjectDate date = new PocketProjectDate();

        if (keyword.equals(CliSyntax.PREFIX_CURRENT.toString())) {
            return date.thisWeekDate(dayOfWeek);
        } else if (keyword.equals(CliSyntax.PREFIX_FUTURE.toString())) {
            return date.nextWeekDate(dayOfWeek);
        } else if (keyword.equals(CliSyntax.PREFIX_PAST.toString())) {
            return date.lastWeekDate(dayOfWeek);
        } else {
            throw new ParseException(PocketProjectDate.DAY_OF_WEEK_MONTH_CONSTRAINTS);
        }
    }

    /**
     * Formats date if user choose with reference to this, next or last month.
     * @param keyword this, next or last.
     * @param numberString target day of the week.
     * @return formatted string in the form DD/MM/YYYY
     * @throws ParseException if the user input does not conform the expected format.
     */
    private static String formatMonthDate(String keyword, String numberString) throws ParseException {

        if (!isValidInput(numberString)) {
            throw new ParseException(PocketProjectDate.DAY_OF_WEEK_MONTH_CONSTRAINTS);
        }

        int dayOfMonth = Integer.parseInt(numberString);
        if (dayOfMonth < FIRST_DAY_OF_MONTH || dayOfMonth > LAST_DAY_OF_MONTH) {
            throw new ParseException(PocketProjectDate.DAY_OF_WEEK_MONTH_CONSTRAINTS);
        }

        boolean validDayInMonth = CalendarDate.isValidDayInMonth(keyword, dayOfMonth);
        if (!validDayInMonth) {
            throw new ParseException(CalendarDate.DAY_MONTH_CONSTRAINTS);
        }

        PocketProjectDate date = new PocketProjectDate();

        if (keyword.equals(CliSyntax.PREFIX_CURRENT.toString())) {
            return date.thisMonthDate(dayOfMonth);
        } else if (keyword.equals(CliSyntax.PREFIX_FUTURE.toString())) {
            return date.nextMonthDate(dayOfMonth);
        } else if (keyword.equals(CliSyntax.PREFIX_PAST.toString())) {
            return date.lastMonthDate(dayOfMonth);
        } else {
            throw new ParseException(PocketProjectDate.DAY_OF_WEEK_MONTH_CONSTRAINTS);
        }
    }


}
