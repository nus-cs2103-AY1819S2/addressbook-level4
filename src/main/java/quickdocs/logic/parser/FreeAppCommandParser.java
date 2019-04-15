package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import quickdocs.logic.commands.FreeAppCommand;
import quickdocs.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code FreeAppCommand} object.
 */
public class FreeAppCommandParser implements Parser<FreeAppCommand> {
    public static final Prefix PREFIX_FORMAT = new Prefix("f/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");

    /**
     * Parses the given {@code String} of arguments in the context of the {@code FreeAppCommand}
     * and returns a {@code FreeAppCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public FreeAppCommand parse(String args) throws ParseException {
        // use dates of next month as default search range if no arguments provided
        if (args.isEmpty()) {
            LocalDate nextMonthDate = LocalDate.now().plusMonths(1);
            List<LocalDate> dates = ParserUtil.parseFormatDate(ParserUtil.FORMAT_MONTH, nextMonthDate);
            assert dates.size() == 2;

            return new FreeAppCommand(dates.get(0), dates.get(1));
        }

        // check if required prefixes are present
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FORMAT, PREFIX_DATE);
        boolean prefixesPresent = arePrefixesPresent(argMultimap, PREFIX_FORMAT, PREFIX_DATE);
        boolean preamblePresent = argMultimap.getPreamble().isEmpty();
        if (!preamblePresent || !prefixesPresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FreeAppCommand.MESSAGE_USAGE));
        }

        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim());
        String format = argMultimap.getValue(PREFIX_FORMAT).get().trim();
        List<LocalDate> dates = ParserUtil.parseFormatDate(format, date);
        assert dates.size() == 2;

        return new FreeAppCommand(dates.get(0), dates.get(1));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
