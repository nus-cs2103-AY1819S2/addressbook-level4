package seedu.finance.logic.parser;

import static seedu.finance.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.finance.logic.parser.CliSyntax.PREFIX_AMOUNT;

import java.util.stream.Stream;

import seedu.finance.logic.commands.SetCommand;
import seedu.finance.logic.parser.exceptions.ParseException;
import seedu.finance.model.record.Amount;

/**
 * Parses input arguments and creates a new SetCommand object
 */
public class SetCommandParser implements Parser<SetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetCommand
     * and returns an SetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        return new SetCommand(amount);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
