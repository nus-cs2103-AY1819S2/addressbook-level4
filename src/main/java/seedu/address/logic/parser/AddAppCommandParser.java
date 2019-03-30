package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddAppCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.patient.Nric;

/**
 * Parses input arguments and creates a new AddAppCommand object
 */
public class AddAppCommandParser implements Parser<AddAppCommand> {
    public static final Prefix PREFIX_NRIC = new Prefix("r/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_START = new Prefix("s/");
    public static final Prefix PREFIX_END = new Prefix("e/");
    public static final Prefix PREFIX_COMMENT = new Prefix("c/");

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppCommand
     * and returns an AddAppCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_DATE, PREFIX_START, PREFIX_END, PREFIX_COMMENT);

        boolean prefixesPresent = arePrefixesPresent(argMultimap, PREFIX_NRIC,
                PREFIX_DATE, PREFIX_START, PREFIX_END, PREFIX_COMMENT);
        boolean preamblePresent = argMultimap.getPreamble().isEmpty();
        if (!prefixesPresent || !preamblePresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE));
        }

        Nric nric = new Nric(argMultimap.getValue(PREFIX_NRIC).get().trim());
        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim());
        LocalTime start = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START).get().trim());
        LocalTime end = ParserUtil.parseTime(argMultimap.getValue(PREFIX_END).get().trim());
        String comment = argMultimap.getValue(PREFIX_COMMENT).get().trim();

        return new AddAppCommand(nric, date, start, end, comment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
