package quickdocs.logic.parser;

import static quickdocs.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import quickdocs.logic.commands.ListAppCommand;
import quickdocs.logic.parser.exceptions.ParseException;
import quickdocs.model.patient.Nric;

/**
 * Parses input arguments and creates a new {@code ListAppCommand} object.
 */
public class ListAppCommandParser implements Parser<ListAppCommand> {
    public static final Prefix PREFIX_FORMAT = new Prefix("f/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_NRIC = new Prefix("r/");

    /**
     * Parses the given {@code String} of arguments in the context of the {@code ListAppCommand}
     * and returns a {@code ListAppCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ListAppCommand parse(String args) throws ParseException {
        // use dates of current week as default search range if no arguments provided
        if (args.isEmpty()) {
            List<LocalDate> dates = ParserUtil.parseFormatDate(ParserUtil.FORMAT_WEEK, LocalDate.now());
            assert dates.size() == 2;

            return new ListAppCommand(dates.get(0), dates.get(1));
        }

        // check if required prefixes are present
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FORMAT, PREFIX_DATE, PREFIX_NRIC);
        boolean listByDate = arePrefixesPresent(argMultimap, PREFIX_FORMAT, PREFIX_DATE);
        boolean listByNric = arePrefixesPresent(argMultimap, PREFIX_NRIC);
        boolean preamblePresent = argMultimap.getPreamble().isEmpty();
        // Wrong format for the following 3 cases:
        // 1. preamble is present
        // 2. both formats of listing, by date and by nric, is present
        // 3. neither formats of listing, by date nor by nric, is present
        if (!preamblePresent
                || (listByDate && listByNric)
                || (!listByDate && !listByNric)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAppCommand.MESSAGE_USAGE));
        }

        // List appointments by date
        if (listByDate) {
            LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim());
            String format = argMultimap.getValue(PREFIX_FORMAT).get().trim();
            List<LocalDate> dates = ParserUtil.parseFormatDate(format, date);
            assert dates.size() == 2;

            return new ListAppCommand(dates.get(0), dates.get(1));
        }

        // List appointments by patient's nric
        assert listByNric;

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
