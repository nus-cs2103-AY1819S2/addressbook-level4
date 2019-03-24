package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;

import java.util.stream.Stream;

import seedu.address.logic.commands.RecordAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.description.Description;

/**
 * Parses input arguments and creates a new RecordAddCommand object
 */
public class RecordAddCommandParser implements Parser<RecordAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RecordAddCommand
     * and returns an RecordAddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RecordAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DESC);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESC) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecordAddCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDesc(argMultimap.getValue(PREFIX_DESC).get());

        return new RecordAddCommand(description);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
