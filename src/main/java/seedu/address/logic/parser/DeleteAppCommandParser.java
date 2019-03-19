package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteAppCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class DeleteAppCommandParser implements Parser<DeleteAppCommand> {
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_START = new Prefix("s/");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAppCommand
     * and returns a DeleteAppCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAppCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_START);

        boolean prefixesPresent = arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_START);
        boolean preamblePresent = argMultimap.getPreamble().isEmpty();
        if (!prefixesPresent || !preamblePresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppCommand.MESSAGE_USAGE));
        }

        LocalDate date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get().trim());
        LocalTime start = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START).get().trim());

        return new DeleteAppCommand(date, start);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
