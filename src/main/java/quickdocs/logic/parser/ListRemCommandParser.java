package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.logic.commands.ListRemCommand;

/**
 * Parses input arguments and creates a new ListAppCommand object
 */
public class ListRemCommandParser implements Parser<ListRemCommand> {
    public static final Prefix PREFIX_FORMAT = new Prefix("f/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");

    /**
     * Parses the given {@code String} of arguments in the context of the ListRemCommand
     * and returns a ListRemCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListRemCommand parse(String args) throws ParseException {
        // Use default range of dates to list if there are no arguments
        if (args.isEmpty()) {
            List<LocalDate> dates = ParserUtil.parseFormatDate(ParserUtil.FORMAT_WEEK, LocalDate.now());
            return new ListRemCommand(dates.get(0), dates.get(1));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FORMAT, PREFIX_DATE);
        boolean prefixesPresent = arePrefixesPresent(argMultimap, PREFIX_FORMAT, PREFIX_DATE);
        boolean preamblePresent = argMultimap.getPreamble().isEmpty();
        if (!preamblePresent || !prefixesPresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRemCommand.MESSAGE_USAGE));
        }

        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim());
        String format = argMultimap.getValue(PREFIX_FORMAT).get().trim();

        List<LocalDate> dates = ParserUtil.parseFormatDate(format, date);
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
