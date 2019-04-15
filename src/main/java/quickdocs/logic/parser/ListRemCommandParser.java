package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import quickdocs.commons.core.index.Index;
import quickdocs.logic.commands.ListRemCommand;
import quickdocs.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ListRemCommand} object.
 */
public class ListRemCommandParser implements Parser<ListRemCommand> {
    public static final Prefix PREFIX_FORMAT = new Prefix("f/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ListRemCommand}
     * and returns a {@code ListRemCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ListRemCommand parse(String args) throws ParseException {
        // use dates of current week as default search range if no arguments provided
        if (args.isEmpty()) {
            List<LocalDate> dates = ParserUtil.parseFormatDate(ParserUtil.FORMAT_WEEK, LocalDate.now());
            assert dates.size() == 2;

            return new ListRemCommand(dates.get(0), dates.get(1));
        }

        // check if required prefixes are present
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FORMAT, PREFIX_DATE, PREFIX_INDEX);
        boolean listRemindersInRange = arePrefixesPresent(argMultimap, PREFIX_FORMAT, PREFIX_DATE);
        boolean listSingleReminder = arePrefixesPresent(argMultimap, PREFIX_INDEX);
        boolean preamblePresent = argMultimap.getPreamble().isEmpty();
        // Wrong format for the following 3 cases:
        // 1. preamble is present
        // 2. both formats of listing, by range of dates and by index, is present
        // 3. neither formats of listing, by range of dates nor by index, is present
        if (!preamblePresent
                || (listRemindersInRange && listSingleReminder)
                || (!listRemindersInRange && !listSingleReminder)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRemCommand.MESSAGE_USAGE));
        }

        // List a single reminder
        if (listSingleReminder) {
            try {
                Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get().trim());
                return new ListRemCommand(index);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRemCommand.MESSAGE_USAGE), pe);
            }
        }

        // List reminders in a range of dates
        assert listRemindersInRange;

        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim());
        String format = argMultimap.getValue(PREFIX_FORMAT).get().trim();
        List<LocalDate> dates = ParserUtil.parseFormatDate(format, date);
        assert dates.size() == 2;

        return new ListRemCommand(dates.get(0), dates.get(1));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
