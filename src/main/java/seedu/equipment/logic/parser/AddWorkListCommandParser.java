package seedu.equipment.logic.parser;

import java.util.stream.Stream;

import seedu.equipment.commons.core.Messages;
import seedu.equipment.logic.commands.AddWorkListCommand;
import seedu.equipment.logic.parser.exceptions.ParseException;
import seedu.equipment.model.WorkList;
import seedu.equipment.model.WorkListId;
import seedu.equipment.model.equipment.Date;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddWorkListCommandParser implements Parser<AddWorkListCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddWorkListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_DATE, CliSyntax.PREFIX_ASSIGNEE,
                        CliSyntax.PREFIX_ID);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_DATE, CliSyntax.PREFIX_ASSIGNEE, CliSyntax.PREFIX_ID)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddWorkListCommand.MESSAGE_USAGE));
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(CliSyntax.PREFIX_DATE).get());
        String assignee = ParserUtil.parseAssignee(argMultimap.getValue(CliSyntax.PREFIX_ASSIGNEE).get());
        WorkListId id = ParserUtil.parseWorkListId(argMultimap.getValue(CliSyntax.PREFIX_ID).get());

        WorkList workList = new WorkList(date.toString(), assignee, id);

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
