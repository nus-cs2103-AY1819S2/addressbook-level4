package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL;

import java.util.stream.Stream;

import seedu.address.logic.commands.ListUnvisitedCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.Postal;

/**
 * Parses input arguments and creates a new NameCommand object
 */
public class ListUnvisitedParser implements Parser<ListUnvisitedCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListUnivisited
     * and returns an ListUnvisited object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListUnvisitedCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POSTAL);

        if (!arePrefixesPresent(argMultimap, PREFIX_POSTAL)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListUnvisitedCommand.MESSAGE_USAGE));
        }

        Postal postal = ParserUtil.parsePostal(argMultimap.getValue(PREFIX_POSTAL).get());

        return new ListUnvisitedCommand(postal);

    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
