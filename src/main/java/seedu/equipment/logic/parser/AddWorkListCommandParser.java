package seedu.equipment.logic.parser;

import java.util.stream.Stream;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.logic.commands.AddWorkListCommand;
import seedu.equipment.logic.parser.exceptions.ParseException;
import seedu.equipment.model.WorkList;

/**
 * Parses input arguments and creates a new AddEquipmentCommand object
 */
public class AddWorkListCommandParser implements Parser<AddWorkListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddEquipmentCommand
     * and returns an AddEquipmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddWorkListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_DATE, CliSyntax.PREFIX_ASSIGNEE);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_DATE, CliSyntax.PREFIX_ASSIGNEE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddWorkListCommand.MESSAGE_USAGE));
        }

        String date = argMultimap.getValue(CliSyntax.PREFIX_NAME).get();
        String assignee = argMultimap.getValue(CliSyntax.PREFIX_ASSIGNEE).get();

        WorkList workList = new WorkList(date, assignee);

        return new AddWorkListCommand(workList);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
