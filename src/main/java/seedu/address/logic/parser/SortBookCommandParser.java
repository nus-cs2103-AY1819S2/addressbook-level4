package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORTTYPE;

import java.util.stream.Stream;

import seedu.address.logic.commands.SortBookCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new SortBookCommand object
 */
public class SortBookCommandParser implements Parser<SortBookCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SortBookCommand
     * and returns an SortBookCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortBookCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_SORTTYPE, PREFIX_ORDER);

        if (!arePrefixesPresent(argMultimap, PREFIX_SORTTYPE, PREFIX_ORDER)
            || !argMultimap.getPreamble().isEmpty()
            || (!argMultimap.getValue(PREFIX_ORDER).get().toUpperCase().equals("ASC")
            && !argMultimap.getValue(PREFIX_ORDER).get().toUpperCase().equals("DES"))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortBookCommand.MESSAGE_USAGE));
        }

        String sortType = argMultimap.getValue(PREFIX_SORTTYPE).get();
        String orderType = argMultimap.getValue(PREFIX_ORDER).get();

        return new SortBookCommand(sortType, orderType);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
