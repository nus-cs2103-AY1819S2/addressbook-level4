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

import seedu.address.logic.commands.FreeAppCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FreeAppCommand object
 */
public class FreeAppCommandParser implements Parser<FreeAppCommand> {
    public static final Prefix PREFIX_FORMAT = new Prefix("f/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final String FORMAT_DAY = "day";
    public static final String FORMAT_WEEK = "week";
    public static final String FORMAT_MONTH = "month";
    private static final String[] FORMATS = {FORMAT_DAY, FORMAT_WEEK, FORMAT_MONTH};

    /**
     * Parses the given {@code String} of arguments in the context of the FreeAppCommand
     * and returns a FreeAppCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FreeAppCommand parse(String args) throws ParseException {
        // Use default range of dates to list if there are no arguments
        if (args.isEmpty()) {
            return new FreeAppCommand();
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FORMAT, PREFIX_DATE);
        boolean prefixesPresent = arePrefixesPresent(argMultimap, PREFIX_FORMAT, PREFIX_DATE);
        boolean preamblePresent = argMultimap.getPreamble().isEmpty();
        if (!preamblePresent || !prefixesPresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeAppCommand.MESSAGE_USAGE));
        }

        LocalDate start = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim());
        String format = argMultimap.getValue(PREFIX_FORMAT).get().trim();
        // check if the format given is valid
        if (Arrays.stream(FORMATS).noneMatch(format::equalsIgnoreCase)) {
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
        return new FreeAppCommand(start, end);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
