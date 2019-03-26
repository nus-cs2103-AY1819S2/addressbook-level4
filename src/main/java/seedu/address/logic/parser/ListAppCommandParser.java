package seedu.address.logic.parser;

import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.ListAppCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Nric;

/**
 * Parses input arguments and creates a new ListAppCommand object
 */
public class ListAppCommandParser implements Parser<ListAppCommand> {
    public static final Prefix PREFIX_FORMAT = new Prefix("f/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_NRIC = new Prefix("r/");
    public static final String FORMAT_DAY = "day";
    public static final String FORMAT_WEEK = "week";
    public static final String FORMAT_MONTH = "month";
    private static final String[] TOPICS = {FORMAT_DAY, FORMAT_WEEK, FORMAT_MONTH};

    /**
     * Parses the given {@code String} of arguments in the context of the ListAppCommand
     * and returns a ListAppCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListAppCommand parse(String args) throws ParseException {
        // Use default range of dates to list if there are no arguments
        if (args.isEmpty()) {
            return new ListAppCommand();
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FORMAT, PREFIX_DATE, PREFIX_NRIC);
        boolean listByDate = arePrefixesPresent(argMultimap, PREFIX_FORMAT, PREFIX_DATE);
        boolean listByNric = arePrefixesPresent(argMultimap, PREFIX_NRIC);
        boolean preamblePresent = argMultimap.getPreamble().isEmpty();
        // Wrong format for the following 3 cases:
        // 1. preamble is present
        // 2. both formats of listing, by date and by nric, is present
        // 3. neither formats of listing, by date nor by nric, is present
        if (!preamblePresent || (listByDate && listByNric) || (!listByDate && !listByNric)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppCommand.MESSAGE_USAGE));
        }

        // List appointments by date
        if (listByDate) {
            LocalDate start = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim());
            String format = argMultimap.getValue(PREFIX_FORMAT).get().trim();
            // check if the format given is valid
            if (Arrays.stream(TOPICS).noneMatch(format::equalsIgnoreCase)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        "Valid keywords for FORMATS: "
                        + FORMAT_DAY + ", "
                        + FORMAT_WEEK + ", "
                        + FORMAT_MONTH));
            }
            LocalDate end;
            switch (format) {
            case "day":
                end = start;
                break;
            case "week":
                end = start.with(nextOrSame(SUNDAY));
                start = start.with(previousOrSame(MONDAY));
                break;
            case "month":
                end = start.with(lastDayOfMonth());
                start = start.with(firstDayOfMonth());
                break;
            default:
                end = start;
            }
            return new ListAppCommand(start, end);
        }

        // List appointments by patient's nric
        Nric nric = new Nric(argMultimap.getValue(PREFIX_NRIC).get().trim());
        return new ListAppCommand(nric);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
