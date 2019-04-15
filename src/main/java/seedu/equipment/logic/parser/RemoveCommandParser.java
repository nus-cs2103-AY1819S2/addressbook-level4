package seedu.equipment.logic.parser;

import java.util.stream.Stream;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.logic.commands.RemoveCommand;
import seedu.equipment.logic.parser.exceptions.ParseException;
import seedu.equipment.model.WorkListId;
import seedu.equipment.model.equipment.SerialNumber;

/**
 * Parses input arguments and creates a new RemoveCommand object
 */
public class RemoveCommandParser implements Parser<RemoveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveCommand
     * and returns a RemoveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_ID, CliSyntax.PREFIX_SERIALNUMBER);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_ID, CliSyntax.PREFIX_SERIALNUMBER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, RemoveCommand.MESSAGE_USAGE));
        }

        SerialNumber serialNumber = ParserUtil.parseSerialNumber(argMultimap.getValue(
                CliSyntax.PREFIX_SERIALNUMBER).get());
        WorkListId id = ParserUtil.parseWorkListId(argMultimap.getValue(CliSyntax.PREFIX_ID).get());

        return new RemoveCommand(id, serialNumber);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
